const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(FOV, SCREEN_WIDTH / SCREEN_HEIGHT, 0.1, 1000);

const renderer = new THREE.WebGLRenderer();
renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
document.body.appendChild(renderer.domElement);

const aprilTags = [
    {
        x: 0,
        y: 0,
        z: 0
    }
];

aprilTags.forEach((tagPos, i) => {
    const aprilTag = new AprilTag(tagPos, `img/a${i + 1}.png`, scene);
    aprilTag.draw();
});



camera.position.z = 5;

function animate() {
    requestAnimationFrame(animate);

    //cube.rotation.x += 0.01;
    //cube.rotation.y += 0.01;

    renderer.render(scene, camera);
};

animate();