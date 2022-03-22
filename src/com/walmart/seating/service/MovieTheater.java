package com.walmart.seating.service;

import java.util.ArrayList;
import java.util.List;

public class MovieTheater {
    private Seat[][] seats;
    private int totalNumberOfSeats;
    private int row;
    private int col;
    private int reserved;
    private int notAvailable;
    private int[] avaliableSeatPerRow;

    public MovieTheater(int row, int col){
        this.row = row;
        this.col = col;
        this.totalNumberOfSeats = row * col;
        this.reserved = 0;
        this.notAvailable = 0;
        seats = new Seat[row][col];
        avaliableSeatPerRow = new int[row];
        for(int i = 0; i < row; i++){
            avaliableSeatPerRow[i] = col;
            for(int j = 0; j < col; j++){
                seats[i][j] = Seat.AVAILABLE;
            }
        }
    }

    public String reserve(int requestNumber){
        //If no seat left
        if(totalNumberOfSeats - reserved - notAvailable < requestNumber){
            return "";
        }

        //start from middle row
        int midRow = row / 2;
        int targetRow = -1;
        targetRow = findTargetRow(targetRow, avaliableSeatPerRow, midRow, requestNumber);

        List<int[]> seatPositions = new ArrayList<int[]>();

        //If one row can hold all reservation
        if(targetRow != -1){
            reserved += requestNumber;
            avaliableSeatPerRow[targetRow] -= requestNumber;
            requestNumber = reserveSeatPerRow(seats, targetRow, requestNumber, seatPositions);

        }else{//If need multiple row to book all reservation

            List<int[]> bufferedReservePosition = new ArrayList<>();
            List<int[]> bufferedCovidPosition = new ArrayList<>();
            while(requestNumber > 0){
                //backtracking if no enough seats to hold reservation
                if(totalNumberOfSeats - reserved - notAvailable < requestNumber){
                    backtracking(bufferedReservePosition, reserved);
                    backtracking(bufferedCovidPosition, notAvailable);
                    return "";
                }
                //try to group one cohort together
                int spaceMax = 0;
                int maxRow = -1;
                maxRow = findTargetRowWithMaxAvaliableSeat(avaliableSeatPerRow, maxRow, spaceMax, midRow, requestNumber);

                //if one row enough for requestSeat
                if(requestNumber >= avaliableSeatPerRow[maxRow]){
                    //requestNumber -= avaliableSeatPerRow[maxRow];
                    reserved += avaliableSeatPerRow[maxRow];
                    avaliableSeatPerRow[maxRow] = 0;
                    requestNumber = reserveSeatPerRow(seats, maxRow, requestNumber, seatPositions);
                }else{
                    //if need multiple row for requestSeat
                    reserved += requestNumber;
                    avaliableSeatPerRow[maxRow] -= requestNumber;
                    requestNumber = reserveSeatPerRow(seats, maxRow, requestNumber, seatPositions);
                }

                //update bufferedPosition in case need to do backtracking
                for(int[] pos : seatPositions){
                    bufferedReservePosition.add(pos);
                    bufferedCovidPosition.addAll(covidSpace(pos[0], pos[1]));
                }

            }
        }
        //add unavlialble space for covid safety issue
        StringBuilder res = new StringBuilder();
        for(int[] pos : seatPositions){
            // (0,0) should be converted to A1
            String curPos = (char)(pos[0] + 65) + String.valueOf(pos[1] + 1) + ",";
            res.append(curPos);
            covidSpace(pos[0], pos[1]);
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }

    //Make 3 seats unavaliable on the left and right of cohort reserved seat
    //Make front row and back row unavaliable of cohort reserved seat
    private List<int[]> covidSpace(int x, int y){
        List<int[]> unavaliable = new ArrayList<int[]>();
        int[] dx = {-1, 0, 1};
        int[] dy = {-3, -2, -1, 0, 1, 2, 3};
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 7; j++){
                int curX = x + dx[i];
                int curY = y + dy[j];
                if(curX >= 0 && curY < col && curX < row && curY >= 0){
                    if(seats[curX][curY] == Seat.AVAILABLE){
                        seats[curX][curY] = Seat.NOTAVAILABLE;
                        notAvailable++;
                        avaliableSeatPerRow[curX]--;
                        unavaliable.add(new int[]{curX, curY});
                    }
                }
            }
        }
        return unavaliable;
    }

    private void backtracking(List<int[]> bufferedPosition, int reserved){
        for(int[] pos : bufferedPosition){
            seats[pos[0]][pos[1]] = Seat.AVAILABLE;
            reserved--;
            avaliableSeatPerRow[pos[0]]++;
        }
    }
    private int findTargetRow(int targetRow, int[] avaliableSeatPerRow, int midRow, int requestNumber){
        for(int i = 0; i <= midRow; i++) {
            //check front half row of seats
            int curRow = midRow - i;
            if (curRow >= 0 && avaliableSeatPerRow[curRow] >= requestNumber) {
                targetRow = curRow;
            }
            if (targetRow != -1) {
                break;
            }
            //check rear half row of seats
            curRow = midRow + i;
            if (curRow < row && avaliableSeatPerRow[curRow] >= requestNumber) {
                targetRow = curRow;
            }
            if (targetRow != -1) {
                break;
            }
        }
        return targetRow;
    }

    private int findTargetRowWithMaxAvaliableSeat(int[] avaliableSeatPerRow, int maxRow, int spaceMax, int midRow, int requestNumber){
        for(int i = 0; i <= midRow; i++){
            //check front half row of seats
            int curRow = midRow - i;
            if(curRow >= 0 && avaliableSeatPerRow[curRow] == requestNumber) {
                spaceMax = Integer.MAX_VALUE;
                maxRow = curRow;
            }else if (curRow >= 0 && avaliableSeatPerRow[curRow] > spaceMax){
                spaceMax = avaliableSeatPerRow[curRow];
                maxRow = curRow;
            }

            //check rear half row of seats
            curRow = midRow + i;
            if(curRow < row && avaliableSeatPerRow[curRow] == requestNumber) {
                spaceMax = Integer.MAX_VALUE;
                maxRow = curRow;
            }else if (curRow < row && avaliableSeatPerRow[curRow] > spaceMax){
                spaceMax = avaliableSeatPerRow[curRow];
                maxRow = curRow;
            }
        }
        return maxRow;
    }

    private int reserveSeatPerRow(Seat[][] seats, int targetRow, int requestNumber, List<int[]> seatPositions){
        for(int i = 0; i < col; i++){
            if(seats[targetRow][i] == Seat.AVAILABLE && requestNumber > 0){
                seats[targetRow][i] = Seat.RESERVED;
                requestNumber--;
                seatPositions.add(new int[]{targetRow, i});
            }
        }
        return requestNumber;
    }
}
