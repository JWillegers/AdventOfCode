import cv2 as cv
import numpy as np
import re


if __name__ == '__main__':
    height = 103
    width = 101

    with open('../Input/day14.txt', 'r') as file:
        raw_input = file.read().split('\n')

    # parse input
    robots = []
    for line in raw_input:
        s = re.split(r'[,= ]+', line)
        robots.append([(int(s[2]), int(s[1])), (int(s[5]), int(s[4]))])

    # load template
    template = cv.imread("../Input/Day14template.png", cv.IMREAD_GRAYSCALE)


    time = 0
    robot_img = np.zeros((height, width), np.uint8)
    while time < 10000:
        time += 1

        # move robots and simultaneously make new image
        robot_img = np.zeros((height,width),np.uint8)
        robot_max_per_cell = 0
        for robot in robots:
            new_row = (robot[0][0] + robot[1][0] + height) % height
            new_col = (robot[0][1] + robot[1][1] + width) % width
            if new_row < 0:
                print("row error")
            if new_col < 0:
                print("col error")
            robot[0] = (new_row, new_col)
            robot_img[new_row][new_col] += 1
            robot_max_per_cell = max(robot_max_per_cell, int(robot_img[new_row][new_col]))

        # normalize robot_img
        for i in range(len(robot_img)):
            for j in range(len(robot_img[i])):
                robot_img[i][j] = 255 * (robot_img[i][j] / robot_max_per_cell)

        corr = cv.matchTemplate(robot_img, template, cv.TM_CCORR_NORMED)
        min_val, max_val, min_loc, max_loc = cv.minMaxLoc(corr)
        if max_val >= 0.33:
            break

    print("Time steps: " + time)
    cv.imwrite("Day14.png", robot_img)
    print("Image saved")