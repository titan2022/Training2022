document.body.addEventListener("keydown", (event) => {
    switch (event.key) {
        case "w":
            robot.setVelocity(0, 0, -5);
            break;
        case "a":
            robot.setVelocity(-5, 0, 0);
            break;
        case "s":
            robot.setVelocity(0, 0, 5);
            break;
        case "d":
            robot.setVelocity(5, 0, 0);
            break;
        case "q":
            robot.setRotation(90 * DEG);
            break;
        case "e":
            robot.setRotation(-90 * DEG);
            break;
    }
});

document.body.addEventListener("keyup", (event) => {
    robot.setVelocity(0, 0, 0);
    robot.setRotation(0);
});