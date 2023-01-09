class AprilTag {
    constructor(position, image, scene) {
        this.position = position;
        this.image = image;
        this.scene = scene;
    }

    draw() {
        const tagGeom = new THREE.BoxGeometry(0.22 * IN, 10.5 * IN, 10.5 * IN);
        const tagLoader = new THREE.TextureLoader();
        const tagMat = new THREE.MeshBasicMaterial({
            map: tagLoader.load(this.image),
        });
        const tag = new THREE.Mesh(tagGeom, tagMat);
        this.scene.add(tag);

        tag.position.x = this.position.x;
        tag.position.y = this.position.y;
        tag.position.z = this.position.z;
    }
}