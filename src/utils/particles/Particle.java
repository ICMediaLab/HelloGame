package utils.particles;

import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

import utils.Position;

public class Particle {
        private Image texture;
        private Position position;
        private Position velocity;
        private float angle;
        private float angularVelocity;
        private Color colour;
        private float size;
        int ttl;

        public Particle(Image texture, Position position, Position velocity,
            float angle, float angularVelocity, Color color, float size, int ttl) {
            this.texture = texture;
            this.position = position;
            this.velocity = velocity;
            this.angle = angle;
            this.angularVelocity = angularVelocity;
            this.colour = color;
            this.size = size;
            this.ttl = ttl;
        }

        public void update() {
            ttl--;
            position.translate(velocity);
            angle += angularVelocity;
        }

        public void render() {
            //Position origin = new Position(texture.getWidth() / 2, texture.getHeight() / 2);
            
            texture.setRotation(angle); // rotate
            texture.draw(position.getX(), position.getY(), size, colour); // render
        }
    
}
