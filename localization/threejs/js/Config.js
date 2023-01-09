const FOV = 60;
const SCREEN_WIDTH = 1920;
const SCREEN_HEIGHT = 1080;
const VIEWPORT_SCALE = 0.4;

const ROBOT_WIDTH = 1;
const ROBOT_HEIGHT = 1;
const ROBOT_DEPTH = 1;

const camerasConfig = [ // **RELATIVE TO THE ROBOT CENTER**
    {
        position: {
            x: 0,
            y: 10,
            z: 0
        },
        rotation: {
            x: -90 * DEG,
            y: 0,
            z: 0
        },
        name: "top view DEBUG",
        used: false
    },
    {
        position: {
            x: 0,
            y: 0,
            z: 0
        },
        rotation: {
            x: 0,
            y: 0 * DEG,
            z: 0
        },
        name: "front side",
        used: true
    },
    {
        position: {
            x: 0,
            y: 0,
            z: 0
        },
        rotation: {
            x: 0,
            y: 180 * DEG,
            z: 0
        },
        name: "back side",
        used: true
    },
    {
        position: {
            x: 0,
            y: 0,
            z: 0
        },
        rotation: {
            x: 0,
            y: 90 * DEG,
            z: 0
        },
        name: "left side",
        used: true
    },
    {
        position: {
            x: 0,
            y: 0,
            z: 0
        },
        rotation: {
            x: 0,
            y: -90 * DEG,
            z: 0
        },
        name: "right side",
        used: true
    }
];

const aprilTags = [
    { // ID 1
        x: 596.51 * IN,
        y: 12.91 * IN,
        z: 269.18 * IN
    },
    { // ID 2
        x: 596.51 * IN,
        y: 12.91 * IN,
        z: 203.18 * IN
    },
    { // ID 3
        x: 596.51 * IN,
        y: 12.91 * IN,
        z: 137.18 * IN
    },
    { // ID 4
        x: 622.7 * IN,
        y: 22.13 * IN,
        z: 45.63 * IN
    },
    { // ID 5
        x: 0,
        y: 22.13 * IN,
        z: 45.63 * IN
    },
    { // ID 6
        x: 26.19 * IN,
        y: 12.91 * IN,
        z: 137.18 * IN
    },
    { // ID 7
        x: 26.19 * IN,
        y: 12.91 * IN,
        z: 203.18 * IN
    },
    { // ID 8
        x: 26.19 * IN,
        y: 12.91 * IN,
        z: 269.18 * IN
    },
];