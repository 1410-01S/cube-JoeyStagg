package com.example.project;

import java.util.Scanner;

//thoughts: draw out on paper how the cube is going to sit (perspective) while being turned then I can brute force everything
//figure out how to log the opposite of the inputs (how to solve after it's mixed up)

// ---------0y 1y 2y
// ---------3y 4y 5y
// ---------6y 7y 8y
// 0o 1o 2o 0b 1b 2b 0r 1r 2r 0g 1g 2g
// 3o 4o 5o 3b 4b 5b 3r 4r 5r 3g 4g 5g
// 6o 7o 8o 6b 7b 8b 6r 7r 8r 6g 7g 8g
// ---------0w 1w 2w
// ---------3w 4w 5w
// ---------6w 7w 8w

public class Cube {
	// This initializes the original cube faces that will inherit temporary values
	// after all changes are made
	static String[][] cubeFaceRed = {
			{ "0r", "1r", "2r" },
			{ "3r", "4r", "5r" },
			{ "6r", "7r", "8r" },
	};

	static String[][] cubeFaceBlue = {
			{ "0b", "1b", "2b" },
			{ "3b", "4b", "5b" },
			{ "6b", "7b", "8b" },
	};

	static String[][] cubeFaceOrange = {
			{ "0o", "1o", "2o" },
			{ "3o", "4o", "5o" },
			{ "6o", "7o", "8o" },
	};

	static String[][] cubeFaceGreen = {
			{ "0g", "1g", "2g" },
			{ "3g", "4g", "5g" },
			{ "6g", "7g", "8g" },
	};

	static String[][] cubeFaceYellow = {
			{ "0y", "1y", "2y" },
			{ "3y", "4y", "5y" },
			{ "6y", "7y", "8y" },
	};

	static String[][] cubeFaceWhite = {
			{ "0w", "1w", "2w" },
			{ "3w", "4w", "5w" },
			{ "6w", "7w", "8w" },
	};


	// This initializes the temporary cube face to write over
	static String[][] cubeFaceTemp = new String[3][3];

	// prints a face
	static void show(String[][] face) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(face[i][j] + " ");
			}
			System.out.println();
		}
	}

	// prints entire cube (all 6 sides)
	static void showEntireCube() {

		
		show(cubeFaceRed);
		System.out.println();
		show(cubeFaceBlue);
		System.out.println();
		show(cubeFaceOrange);
		System.out.println();
		show(cubeFaceGreen);
		System.out.println();
		show(cubeFaceYellow);
		System.out.println();
		show(cubeFaceWhite);

	}

	static void rotateRow(String[][] face1, String[][] face2, String[][] face3, String[][] face4, int row){
		String[][] tempRow = new String[4][3]; //First [] is temp spot # second [] is row values
		for(int i = 0; i < 3; i++){
			tempRow[0][i] = face1[row][i]; //save top red
			tempRow[1][i] = face2[row][i]; //save top blue
			tempRow[2][i] = face3[row][i]; //save top green
			tempRow[3][i] = face4[row][i]; //save top orange
			face1[row][i] = tempRow[2][i];
			face2[row][i] = tempRow[0][i];
			face3[row][i] = tempRow[3][i];
			face4[row][i] = tempRow[1][i];
		}
	}

	static void rotateCol(String[][] face1, String[][] face2, String[][] face3, String[][] face4, int col){
		String[][] tempCol = new String[3][4]; //First [] is temp spot # second [] is col values
		for(int i = 0; i < 3; i++){
			tempCol[i][0] = face1[i][col];//save right yellow
			tempCol[i][1] = face2[i][col];//save right blue
			tempCol[i][2] = face3[i][col];//save right white
			tempCol[i][3] = face4[i][col];//save right green
			face1[i][col] = tempCol[i][1];
			face2[i][col] = tempCol[i][2];
			face3[i][col] = tempCol[i][3];
			face4[i][col] = tempCol[i][0];
		}
	}

	static void swapCol(String[][] face1){                    //somethings wrong
		String[] temp = new String[3];
		for(int i = 0; i < 3; i++){
			temp[i] = face1[i][0];
			face1[i][0] = face1[i][2];
			face1[i][2] = temp[i];
		}
	}

	// This method uses boolean for clockwise or counterclockwise and changes the
	// values of the face in order to mimick rotation
	static void rotate(String cube[][], boolean clockwise) {
		// copys values of cube to temp cube
		for (int i = 0; i < cube.length; i++) {
			for (int j = 0; j < cube.length; j++) {
				cubeFaceTemp[i][j] = cube[i][j];
			}
		}

		// turns face clockwise
		if (clockwise) {
			for (int i = 0; i < cube.length; i++) {
				for (int j = 0; j < cube.length; j++) {
					if (cube[i][j] == cube[i][2]) {
						cubeFaceTemp[i][j] = cube[j - 2][i];
					} else if (cube[i][j] == cube[i][1]) {
						cubeFaceTemp[i][j] = cube[j][i];
					} else if (cube[i][j] == cube[i][0]) {
						cubeFaceTemp[i][j] = cube[j + 2][i];
					}
				}
			}
			// copys the value of the temporary face to the cube so that the next loop
			// has updated information
			for (int i = 0; i < cube.length; i++) {
				for (int j = 0; j < cube.length; j++) {
					cube[i][j] = cubeFaceTemp[i][j];
				}
			}
			// turns face counterclockwise
		} else if (!clockwise) {
			for (int ccl = 0; ccl < 3; ccl++) { // This top loop is because going cl 3 times is equal to going ccl once
				for (int i = 0; i < cube.length; i++) {
					for (int j = 0; j < cube.length; j++) {
						if (cube[i][j] == cube[i][2]) {
							cubeFaceTemp[i][j] = cube[j - 2][i];
						} else if (cube[i][j] == cube[i][1]) {
							cubeFaceTemp[i][j] = cube[j][i];
						} else if (cube[i][j] == cube[i][0]) {
							cubeFaceTemp[i][j] = cube[j + 2][i];
						}
					}
				}

				// this copys the value of the temporry face to the cube so that the next loop
				// has
				// updated information
				for (int i = 0; i < cube.length; i++) {
					for (int j = 0; j < cube.length; j++) {
						cube[i][j] = cubeFaceTemp[i][j];
					}
				}
			}
		}

	}

	// Based on user input it rotates a specific face in a specific direction
	static void faceRotate(String input) {

		switch (input) {
			case "U":
				rotate(cubeFaceYellow, true);
				rotateRow(cubeFaceRed, cubeFaceBlue, cubeFaceGreen, cubeFaceOrange, 0);
				break;
			case "U'":
				rotate(cubeFaceYellow, false);
				rotateRow(cubeFaceRed, cubeFaceBlue, cubeFaceGreen, cubeFaceOrange, 0);
				rotateRow(cubeFaceRed, cubeFaceBlue, cubeFaceGreen, cubeFaceOrange, 0);
				rotateRow(cubeFaceRed, cubeFaceBlue, cubeFaceGreen, cubeFaceOrange, 0);
				break;
			case "D":
				rotate(cubeFaceWhite, true);
				rotateRow(cubeFaceRed, cubeFaceBlue, cubeFaceGreen, cubeFaceOrange, 2);
				rotateRow(cubeFaceRed, cubeFaceBlue, cubeFaceGreen, cubeFaceOrange, 2);
				rotateRow(cubeFaceRed, cubeFaceBlue, cubeFaceGreen, cubeFaceOrange, 2);
				break;
			case "D'":
				rotate(cubeFaceWhite, false);
				rotateRow(cubeFaceRed, cubeFaceBlue, cubeFaceGreen, cubeFaceOrange, 2);

				break;
			case "R":
				rotate(cubeFaceRed, true);
				rotateCol(cubeFaceYellow, cubeFaceBlue, cubeFaceWhite, cubeFaceGreen, 2);
				swapCol(cubeFaceGreen);
				break;
			case "R'":
				rotate(cubeFaceRed, false);
				rotateCol(cubeFaceYellow, cubeFaceBlue, cubeFaceWhite, cubeFaceGreen, 2);
				rotateCol(cubeFaceYellow, cubeFaceBlue, cubeFaceWhite, cubeFaceGreen, 2);
				rotateCol(cubeFaceYellow, cubeFaceBlue, cubeFaceWhite, cubeFaceGreen, 2);
				// swapCol(cubeFaceGreen);
				break;
			case "L":
				rotate(cubeFaceOrange, true);
				rotateCol(cubeFaceYellow, cubeFaceBlue, cubeFaceWhite, cubeFaceGreen, 0);
				rotateCol(cubeFaceYellow, cubeFaceBlue, cubeFaceWhite, cubeFaceGreen, 0);
				rotateCol(cubeFaceYellow, cubeFaceBlue, cubeFaceWhite, cubeFaceGreen, 0);
				swapCol(cubeFaceGreen);
				break;
			case "L'":
				rotate(cubeFaceOrange, false);
				rotateCol(cubeFaceYellow, cubeFaceBlue, cubeFaceWhite, cubeFaceGreen, 0);
				// swapCol(cubeFaceGreen);
				break;
			case "F":
				rotate(cubeFaceBlue, true);
				break;
			case "F'":
				rotate(cubeFaceBlue, false);
				break;
			case "B":
				rotate(cubeFaceGreen, true);
				break;
			case "B'":
				rotate(cubeFaceGreen, false);
				break;
			default:
				System.out.println("Unrecognized input");
				break;

		}
	}

	// This is the main method
	public static void main(String[] args) {
		
	
		Scanner in = new Scanner(System.in);

		// To see what the cube looks like before you make rotations
		showEntireCube();

		// loop so user can keep making rotations
		boolean keepGoing = true;
		while (keepGoing) {
			System.out.println(
					"Enter (U, U', D, D', R, R', L, L', F, F', B, B') to rotate a side or \"stop\" to exit loop");
			String input = in.next();
			if (!"stop".equals(input)) {
				faceRotate(input);
			} else {
				keepGoing = false;
			}

		}

		System.out.println();

		// To see what cube looks like after rotations
		showEntireCube();

		in.close();
	}
}
