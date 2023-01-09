// Initiating scene
const scene = new THREE.Scene()

// Initiating robot
let views = [];
camerasConfig.forEach((config, i) => {
    const camera = new THREE.PerspectiveCamera(FOV, SCREEN_WIDTH / SCREEN_HEIGHT, 0.1, 1000);

    const viewContainer = document.createElement("div");
    document.getElementById("viewContainer").appendChild(viewContainer);

    const renderer = new THREE.WebGLRenderer({
        preserveDrawingBuffer: true 
    });
    renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    renderer.domElement.style.width = `${SCREEN_WIDTH * VIEWPORT_SCALE}px`;
    renderer.domElement.style.height = `${SCREEN_HEIGHT * VIEWPORT_SCALE}px`;
    viewContainer.appendChild(renderer.domElement);

    const cameraLabel = document.createElement("label");
    cameraLabel.innerHTML = `Camera ${i} (${config.name})`;
    viewContainer.appendChild(cameraLabel);

    views.push({
        renderer: renderer,
        camera: camera,
        cameraPos: config.position,
        cameraRot: config.rotation,
        cameraUsed: config.used
    });
});
const robot = new Robot(views, scene);

// Draw tags
aprilTags.forEach((tagPos, i) => {
    const aprilTag = new AprilTag(tagPos, `img/a${i + 1}.png`, scene);
    aprilTag.draw();
});

// Draw floor
const floorGeom = new THREE.BoxGeometry(622.7 * IN, 1, 8);
const floorMat = new THREE.MeshBasicMaterial({ color: 0x404040 });
const floor = new THREE.Mesh(floorGeom, floorMat);
scene.add(floor);
floor.position.x = floor.geometry.parameters.width / 2; // (x0, z0) is at top left corner
floor.position.y = -floor.geometry.parameters.height / 2;
floor.position.z = floor.geometry.parameters.depth / 2;

let lastTime = 0;
const animate = (currentTime) => {
    let frameTime = currentTime - lastTime;
    lastTime = currentTime;
    if (!frameTime) frameTime = 0;
    requestAnimationFrame(animate);

    robot.update(frameTime / 1000);
    robot.render(scene);
};

animate();