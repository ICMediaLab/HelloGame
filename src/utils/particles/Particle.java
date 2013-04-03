package utils.particles;

import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

import utils.Position;

public class Particle {
        private Image Texture;
        private Position Position;
        private Position Velocity;
        private float Angle;
        private float AngularVelocity;
        private Color Color;
        private float Size;
        int TTL;

        public Particle(Image texture, Position position, Position velocity,
            float angle, float angularVelocity, Color color, float size, int ttl) {
            Texture = texture;
            Position = position;
            Velocity = velocity;
            Angle = angle;
            AngularVelocity = angularVelocity;
            Color = color;
            Size = size;
            TTL = ttl;
        }

        public void update() {
            TTL--;
            Position.translate(Velocity);
            Angle += AngularVelocity;
        }

        public void render() {
            Position origin = new Position(Texture.getWidth() / 2, Texture.getHeight() / 2);
            
            Texture.setRotation(Angle); // rotate
            Texture.draw(Position.getX(), Position.getY(), Size, Color); // render
        }
    
}
