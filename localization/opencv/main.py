from pupil_apriltags import Detector
import cv2
import numpy as np

at_detector = Detector(
   families="tag16h5",
   nthreads=1,
   quad_decimate=1.0,
   quad_sigma=0.0,
   refine_edges=1,
   decode_sharpening=0.25,
   debug=0
)

img = cv2.imread("img/c3_1673227240936.png", cv2.IMREAD_UNCHANGED)
img_gray = cv2.cvtColor(img, cv2.COLOR_BGRA2GRAY)

out = at_detector.detect(img_gray)

print(out[0])
new_img = img
for tag in out:
   new_img = cv2.polylines(new_img, np.int32([tag.corners]), True, (0, 255, 0), 2)
   new_img = cv2.circle(new_img, np.int32(tag.center), 2, (255, 0, 0), 2)

new_img = cv2.resize(new_img, (960, 540))

while(1):
    cv2.imshow("localization test", new_img)
    # ESC char
    if cv2.waitKey(20) & 0xFF == 27:
        break

cv2.destroyAllWindows()
