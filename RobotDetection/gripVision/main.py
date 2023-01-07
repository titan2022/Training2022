# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import griptwo
import numpy as np
from PIL import Image
import cv2

def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    test_img = Image.open('C:\\Users\\tingw\\PyCharmProjects\\gripImageDetection\\venv\\Lib\\test.png');
    test_img_array = np.asarray(test_img)
    print_hi('PyCharm')
    grip = griptwo.ContourTest()
    convexhulls = grip.process(test_img_array)
    Image.fromarray(cv2.drawContours(test_img_array, convexhulls, -1, (0, 255, 0), 3)).save("C:\\Users\\tingw\\PyCharmProjects\\gripImageDetection\\venv\\Lib\\convexhulls.png")
    for convexhull in convexhulls:
        x, y, w, h = cv2.boundingRect(convexhull)
        cv2.circle(test_img_array ,(int(x+w/2), int(y+h/2)), 10, (0, 0, 255), 2)
    Image.fromarray(test_img_array).save("C:\\Users\\tingw\\PyCharmProjects\\gripImageDetection\\venv\\Lib\\final.png")

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
