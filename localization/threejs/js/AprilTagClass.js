class AprilTag {
    constructor(position, image, scene) {
        this.position = position;
        this.image = image;
        this.scene = scene;
    }

    draw() {
        const geometry = new THREE.BoxGeometry(10.5 * IN, 10.5 * IN, 0.22 * IN);
        const loader = new THREE.TextureLoader();
        console.log(this.image)
        const material = new THREE.MeshBasicMaterial({
            map: loader.load(this.image),
        });
        const cube = new THREE.Mesh(geometry, material);
        this.scene.add(cube);
    }
}