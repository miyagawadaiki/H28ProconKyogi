import cv2
import numpy as np
import sys

def drawPoints():
    for i in range(len(points)):
        if i == target_index:
            cv2.circle(img, tuple(points[i]), 3, (0, 0, 255), -1)
        else:
            cv2.circle(img, tuple(points[i]), 3, (255, 0, 0), -1)

def onMouse(event, x, y, flag, params):
    global target_index, isZoom, points
    winname_img, img, winname_pts = params

    # exit if zoom
    if isZoom:
        return
    else:
        # reset img
        img = cv2.imread(sys.argv[1])

    # show cross hair
    if event == cv2.EVENT_MOUSEMOVE:
        tmp = np.copy(img)
        h, w = tmp.shape[0], tmp.shape[1]
        cv2.line(tmp, (x, 0), (x, h - 1), (0, 255, 0))
        cv2.line(tmp, (0, y), (w - 1, y), (0, 255, 0))
        # cv2.imshow(winname_img, tmp)

        # set target point
        for i in range(len(points)):
            if target_index != -1 and i == target_index:
                cv2.circle(tmp, tuple(points[i]), 3, (0, 0, 255), -1)
            else:
                cv2.circle(tmp, tuple(points[i]), 3, (255, 0, 0), -1)
            if np.fabs(points[i][0]-x) < 5:
                if np.fabs(points[i][1]-y) < 5:
                    target_index = i
                    # color change
                    cv2.circle(tmp, tuple(points[i]), 3, (0, 0, 255), -1)
        cv2.imshow(winname_img, tmp)

        if 1 < len(points):
            pts = np.copy(img)
            for x in range(len(points)-1):
                cv2.line(pts, tuple(points[x]), tuple(points[x+1]), (0, 0, 255), 2)
            cv2.line(pts, tuple(points[0]), tuple(points[-1]), (0, 0, 255), 2)
            cv2.imshow(winname_pts, pts)

    if event == cv2.EVENT_LBUTTONDOWN:
        # print(x, y)
        points.append([x,y])

def zoomImage(img, x, y, zoom_rate):
    h, w = img.shape[0], img.shape[1]
    _h = int((h / zoom_rate) / 2)
    _w = int((w / zoom_rate) / 2)
    # cut image
    y1 = y - _h
    y2 = y + _h
    x1 = x - _w
    x2 = x + _w
    drawPoints()
    _img = img[y1:y2, x1:x2]
    _img = cv2.resize(_img, (w, h))
    return _img
    # cv2.imshow(winname_img, _img)

def onTrackbarChange(trackbarValue):
    global isZoom
    if trackbarValue == 1:
        isZoom = True
        if target_index != -1:
            x, y = points[target_index]
            _img = zoomImage(img, x, y, 10)
            cv2.imshow(winname_img, _img)
    else:
        isZoom = False

if 2 != len(sys.argv):
    print('usage: %s image' % sys.argv[0])
    quit()

points = []
img = cv2.imread(sys.argv[1])
winname_img = 'Image'
winname_pts = 'Points'
target_index =-1
isZoom = False
vertexes = []

KEY_ENTER = 13
KEY_ESC = 27
KEY_CURSORKEY_TOP = 63232
KEY_CURSORKEY_BOTTOM = 63233
KEY_CURSORKEY_RIGHT = 63235
KEY_CURSORKEY_LEFT = 63234

cv2.namedWindow(winname_img)
cv2.setMouseCallback(winname_img, onMouse, [winname_img, img, winname_pts])
cv2.imshow(winname_img, img)
cv2.imshow(winname_pts, img)
cv2.moveWindow(winname_img, 0, 0)
cv2.moveWindow(winname_pts, img.shape[1], 0)
cv2.createTrackbar('scope', winname_img, 0, 2, onTrackbarChange)

# exit if esc
while True:
    key = cv2.waitKey(0)
    # print(str(key))

    if key == KEY_ESC:
        break
    if key == KEY_ENTER:
        vertexes.append(points)
        points = []
        # reset img
        img = cv2.imread(sys.argv[1])
    if target_index != -1:
        if key == KEY_CURSORKEY_TOP:
            points[target_index][1] -= 1
        elif key == KEY_CURSORKEY_BOTTOM:
            points[target_index][1] += 1
        elif key == KEY_CURSORKEY_RIGHT:
            points[target_index][0] += 1
        elif key == KEY_CURSORKEY_LEFT:
            points[target_index][0] -= 1
        
        # reset img
        img = cv2.imread(sys.argv[1])
        
        drawPoints()
        if isZoom:
            x, y = points[target_index]
            img = zoomImage(img, x, y, 10)
        cv2.imshow(winname_img, img)

with open(sys.argv[1]+'.txt', 'w') as fout:
    for points in vertexes:
        fout.write(str(len(points))+'\n')
        print(len(points))
        for p in points:
            print(p)
            fout.write(str(p[0])+'  '+str(p[1])+'\n')
    # fout.write('\n'.join('%s %s' % tuple(p) for p in points))
cv2.destroyAllWindows()
