package com.example.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//This program creates and mimick rotations on a rubik's cube
//Author: Joey Stagg
//Version: CS 1410 Spring 2023

public class Cube {
	// cube rotations based on flat layout:
	// . . . . y y y .
	// . . . . y y y .
	// . . . . y y y .
	// . . . . . . . .
	// o o o . b b b . r r r . g g g
	// o o o . b b b . r r r . g g g
	// o o o . b b b . r r r . g g g
	// . . . . . . . .
	// . . . . w w w .
	// . . . . w w w .
	// . . . . w w w .

	// Creates entire cube
	static String[][][] cube = {
			{
					{ "0r", "1r", "2r" },
					{ "3r", "4r", "5r" },
					{ "6r", "7r", "8r" },
			},

			{
					{ "0b", "1b", "2b" },
					{ "3b", "4b", "5b" },
					{ "6b", "7b", "8b" },
			},

			{
					{ "0o", "1o", "2o" },
					{ "3o", "4o", "5o" },
					{ "6o", "7o", "8o" },
			},

			{
					{ "0g", "1g", "2g" },
					{ "3g", "4g", "5g" },
					{ "6g", "7g", "8g" },
			},

			{
					{ "0y", "1y", "2y" },
					{ "3y", "4y", "5y" },
					{ "6y", "7y", "8y" },
			},

			{
					{ "0w", "1w", "2w" },
					{ "3w", "4w", "5w" },
					{ "6w", "7w", "8w" },
			},
	};

	static ArrayList<String> solve = new ArrayList<>();

	// This method prints out the entire cube
	static void show(String[][][] cube) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					System.out.print(cube[i][j][k] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	};

	// This method rotates a givven face either clockwise or counterclockwise
	static void rotate(String[][][] cube, boolean clockwise, int face) {
		String[][][] temp = new String[6][3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp[face][i][j] = cube[face][i][j];
			}
		}

		if (clockwise) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (cube[face][i][j] == cube[face][i][2]) {
						temp[face][i][j] = cube[face][j - 2][i];
					} else if (cube[face][i][j] == cube[face][i][1]) {
						temp[face][i][j] = cube[face][j][i];
					} else if (cube[face][i][j] == cube[face][i][0]) {
						temp[face][i][j] = cube[face][j + 2][i];
					}
				}
			}
			// Saves changes made to cube
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					cube[face][i][j] = temp[face][i][j];
				}
			}
		} else if (!clockwise) {

			for (int ccl = 0; ccl < 3; ccl++) { // This for loop is because going clockwise 3 times == counterclockwise
												// 1 time
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (cube[i][j] == cube[i][2]) {
							temp[face][i][j] = cube[face][j - 2][i];
						} else if (cube[face][i][j] == cube[face][i][1]) {
							temp[face][i][j] = cube[face][j][i];
						} else if (cube[face][i][j] == cube[face][i][0]) {
							temp[face][i][j] = cube[face][j + 2][i];
						}
					}
				}
				// Saves changes made to cube
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						cube[face][i][j] = temp[face][i][j];
					}
				}
			}
		}

	}

	// This method rotates the connecting edge colors based on the face that is
	// being turned
	static void rotateEdge(String[][][] cube, int color, int row, int col, int row2, int col2) {
		String[][][] temp = new String[4][3][3];
		switch (color) {
			case 0:// red face (R)
				int j = 3; // j is for inversing row/col
				for (int i = 0; i < 3; i++) { // rotateEdge(cube, 0, 0, 2, 0, 0);
					j--;
					temp[0][i][col] = cube[1][i][col];// blue
					temp[1][i][col] = cube[4][i][col];// yellow
					temp[2][i][col] = cube[5][i][col];// white
					temp[3][i][col] = cube[3][j][col2];// green
					cube[1][i][col] = temp[2][i][col];// right blue turns white
					cube[4][i][col] = temp[0][i][col];// right yellow turns blue
					cube[5][i][col] = temp[3][i][col];// right white turns green
					cube[3][j][col2] = temp[1][i][col];// left green turns yellow

				}
				break;
			case 1:// blue face (F)
				for (int i = 0; i < 3; i++) { // rotateEdge(cube, 1, 2, 2, 0, 0)
					temp[0][i][col] = cube[2][i][col];// orange
					temp[1][row][i] = cube[4][row][i];// yellow
					temp[2][i][col2] = cube[0][i][col2];// red
					temp[3][row2][i] = cube[5][row2][i];// white
					cube[2][i][col] = temp[3][row2][i];// right orange turns white
					cube[4][row][i] = temp[0][i][col];// bottom yellow turns orange
					cube[0][i][col2] = temp[1][row][i];// left red turns yellow
					cube[5][row2][i] = temp[2][i][col2];// top white turns red
				}
				break;
			case 2:// orange face (L)
				j = 3; // j is for inversing row/col
				for (int i = 0; i < 3; i++) { // rotateEdge(cube, color:2, row:0, col:0, row2:0, col2:2);
					j--;
					temp[0][i][col] = cube[1][i][col];// blue
					temp[1][i][col] = cube[4][i][col];// yellow
					temp[2][i][col] = cube[5][i][col];// white
					temp[3][i][col] = cube[3][j][col2];// green
					cube[1][i][col] = temp[1][i][col];// left blue turns white
					cube[4][i][col] = temp[3][i][col];// left yellow turns blue
					cube[5][i][col] = temp[0][i][col];// left white turns green
					cube[3][j][col2] = temp[2][i][col];// right green turns yellow

				}
				break;
			case 3:// green face (B)

				for (int i = 0; i < 3; i++) { // rotateEdge(cube, 3, 0, 0, 2, 2);
					temp[0][i][col] = cube[2][i][col];// orange
					temp[1][row][i] = cube[4][row][i];// yellow
					temp[2][i][col2] = cube[0][i][col2];// red
					temp[3][row2][i] = cube[5][row2][i];// white
					cube[2][i][col] = temp[3][row2][i];// right orange turns white
					cube[4][row][i] = temp[0][i][col];// bottom yellow turns orange
					cube[0][i][col2] = temp[1][row][i];// left red turns yellow
					cube[5][row2][i] = temp[2][i][col2];// top white turns red
				}

				break;
			case 4:// yellow face (U)
				for (int i = 0; i < 3; i++) { // rotateEdge(cube, 4, 0, 0, 0, 0);
					temp[0][row][i] = cube[0][row][i];// red
					temp[1][row][i] = cube[1][row][i];// blue
					temp[2][row][i] = cube[2][row][i];// orange
					temp[3][row][i] = cube[3][row][i];// green
					cube[0][row][i] = temp[3][row][i];// top red turns green
					cube[1][row][i] = temp[0][row][i];// top blue turns red
					cube[2][row][i] = temp[1][row][i];// top orange turns blue
					cube[3][row][i] = temp[2][row][i];// top green turns orange
				}
				break;
			case 5:// white face (D)
				for (int i = 0; i < 3; i++) { // rotateEdge(cube, 4, 2, 0, 0, 0);
					temp[0][row][i] = cube[0][row][i];// red
					temp[1][row][i] = cube[1][row][i];// blue
					temp[2][row][i] = cube[2][row][i];// orange
					temp[3][row][i] = cube[3][row][i];// green
					cube[0][row][i] = temp[3][row][i];// top red turns green
					cube[1][row][i] = temp[0][row][i];// top blue turns red
					cube[2][row][i] = temp[1][row][i];// top orange turns blue
					cube[3][row][i] = temp[2][row][i];// top green turns orange
				}
				break;
		}

	}

	static void solveCube() {
		System.out.print("To solve enter: ");
		for (int i = solve.size() - 1; i >= 0; i--) {
			System.out.print(solve.get(i) + "  ");
		}
		System.out.println();
	}

	// This is the main method
	public static void main(String[] args)
			throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		show(cube);

		boolean argsCheck = false;
		int argsRunIndex = 0;

		if (args.length > 0) {
			argsCheck = true;
		}
		System.out.println(
				"Enter (u, u', d, d', r, r', l, l', f, f', b, b') to rotate a side or \"show\" to see current cube or \"solve\" to see steps to solve current cube or \"stop\" to quit");
		boolean keepGoing = true;
		while (keepGoing) {
			String input;
			if (!argsCheck) {
				input = reader.readLine();
			} else {
				if (argsRunIndex == args.length) {
					argsCheck = false;
					input = "stop";
				} else {
					input = args[argsRunIndex];
					argsRunIndex++;
				}
			}

			switch (input) {
				case "u":
					rotate(cube, true, 4);
					rotateEdge(cube, 4, 0, 0, 0, 0);
					solve.add("u'");
					break;
				case "u'":
					rotate(cube, false, 4);
					rotateEdge(cube, 4, 0, 0, 0, 0);
					rotateEdge(cube, 4, 0, 0, 0, 0);
					rotateEdge(cube, 4, 0, 0, 0, 0);
					solve.add("u");
					break;
				case "d":
					rotate(cube, true, 5);
					rotateEdge(cube, 4, 2, 0, 0, 0);
					rotateEdge(cube, 4, 2, 0, 0, 0);
					rotateEdge(cube, 4, 2, 0, 0, 0);
					solve.add("d'");

					break;
				case "d'":
					rotate(cube, false, 5);
					rotateEdge(cube, 4, 2, 0, 0, 0);
					solve.add("d");
					break;
				case "r":
					rotate(cube, true, 0);
					rotateEdge(cube, 0, 0, 2, 0, 0);
					solve.add("r'");
					break;
				case "r'":
					rotate(cube, false, 0);
					rotateEdge(cube, 0, 0, 2, 0, 0);
					rotateEdge(cube, 0, 0, 2, 0, 0);
					rotateEdge(cube, 0, 0, 2, 0, 0);
					solve.add("r");
					break;
				case "l":
					rotate(cube, true, 2);
					rotateEdge(cube, 2, 0, 0, 0, 2);
					solve.add("l'");
					break;
				case "l'":
					rotate(cube, false, 2);
					rotateEdge(cube, 2, 0, 0, 0, 2);
					rotateEdge(cube, 2, 0, 0, 0, 2);
					rotateEdge(cube, 2, 0, 0, 0, 2);
					solve.add("l");
					break;
				case "f":
					rotate(cube, true, 1);
					rotateEdge(cube, 1, 2, 2, 0, 0);
					solve.add("f'");
					break;
				case "f'":
					rotate(cube, false, 1);
					rotateEdge(cube, 1, 2, 2, 0, 0);
					rotateEdge(cube, 1, 2, 2, 0, 0);
					rotateEdge(cube, 1, 2, 2, 0, 0);
					solve.add("f");
					break;
				case "b":
					rotate(cube, true, 3);
					rotateEdge(cube, 3, 0, 0, 2, 2);
					rotateEdge(cube, 3, 0, 0, 2, 2);
					rotateEdge(cube, 3, 0, 0, 2, 2);
					solve.add("b'");
					break;
				case "b'":
					rotate(cube, false, 3);
					rotateEdge(cube, 3, 0, 0, 2, 2);
					solve.add("b");
					break;
				case "solve":
					solveCube();
					break;
				case "stop":
					keepGoing = false;
					break;
				case "show":
					show(cube);
					break;
				default:
					System.out.println("Unrecognized input");
					break;

			}

		}
		show(cube);
	}
}
