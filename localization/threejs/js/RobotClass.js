class Robot {
    constructor(views, scene) {
        this.views = views;
        this.velocity = {
            x: 0,
            y: 0,
            z: 0
        };
        this.rotVelocity = 0;

        const robotGeom = new THREE.BoxGeometry(ROBOT_WIDTH, ROBOT_HEIGHT, ROBOT_DEPTH);
        const robotMat = new THREE.MeshBasicMaterial({ color: 0x00FF00 });
        let robot = new THREE.Mesh(robotGeom, robotMat);
        scene.add(robot);

        robot.position.set(622.7 * IN / 2, 1, 4);

        this.robot = robot;
    }

    setVelocity(x, y, z) {
        this.velocity = {
            x: x,
            y: y,
            z: z
        };
    }

    getVelocity() {
        return this.velocity;
    }

    setRotation(newRot) {
        this.rotVelocity = newRot;
    }

    getRotation() {
        return this.rotVelocity;
    }

    update(frameTime) {
        this.robot.position.set(
            this.robot.position.x + this.velocity.x * frameTime,
            this.robot.position.y + this.velocity.y * frameTime,
            this.robot.position.z + this.velocity.z * frameTime,
        );

        this.robot.rotation.y += this.rotVelocity * frameTime;

        this.views.forEach(view => {
            view.camera.position.set(
                this.robot.position.x + view.cameraPos.x,
                this.robot.position.y + view.cameraPos.y,
                this.robot.position.z + view.cameraPos.z,
            );

            if (view.cameraUsed) {
                view.camera.rotation.set(
                    this.robot.rotation.x + view.cameraRot.x,
                    this.robot.rotation.y + view.cameraRot.y,
                    this.robot.rotation.z + view.cameraRot.z,
                );
            } else {
                view.camera.rotation.set(
                    view.cameraRot.x,
                    view.cameraRot.y,
                    view.cameraRot.z,
                );
            }
        });
    }

    render(scene) {
        this.views.forEach(view => {
            view.camera.rotation.y += 0.01;
            view.camera.rotation.z += 0.01;
            view.renderer.render(scene, view.camera);
        });
    }

    saveViews() {
        this.views.forEach((view, i) => {
            if (!view.cameraUsed) {
                return;
            }

            const canvas = view.renderer.domElement;
            const downloadLink = document.createElement("a");
            downloadLink.setAttribute("href", canvas.toDataURL("image/png").replace("image/png", "image/octet-stream"));
            downloadLink.setAttribute("download", `c${i}_${Date.now()}.png`);
            document.body.appendChild(downloadLink);
            downloadLink.click();

            document.getElementById("robotInfo").innerHTML = `
                position: ${this.robot.position.x}, ${this.robot.position.y}, ${this.robot.position.z}<br>
                rotation: ${this.robot.rotation.x}, ${this.robot.rotation.y}, ${this.robot.rotation.z}
            `;
        });
    }
}