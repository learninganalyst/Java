import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.jogamp.java3d.AmbientLight;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Background;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.DirectionalLight;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;
public class BouncingBall3D extends JFrame {
	// This TransformGroup controls the ball's movement and shape.
    private TransformGroup ballTrans;

    // Animation timer.
    private Timer timer;

    // Animation variables.
    private double phase = 0.0;
    private double speed = 0.05;
    private double xPosition = 0.0;
    private boolean paused = false;

    private static final float BALL_RADIUS = 0.18f;
    private static final float GROUND_Y = -0.55f;
    private static final float BOUNCE_HEIGHT = 1.1f;

    public BouncingBall3D() {

        // Create the window.
        setTitle("Java 3D Bouncing Ball");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the 3D canvas.
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        canvas.setFocusable(true);

        add(canvas, BorderLayout.CENTER);

        // Create the Java 3D universe.
        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();

        // Create the scene.
        BranchGroup scene = createSceneGraph();

        // Add the scene to the universe.
        universe.addBranchGraph(scene);

        // Add keyboard interaction.
        addKeyboardControls(canvas);

        // Start animation.
        timer = new Timer(16, e -> animateBall());
        timer.start();
    }

    private BranchGroup createSceneGraph() {

        BranchGroup root = new BranchGroup();

        BoundingSphere bounds =
                new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        // Background color.
        Background background = new Background(new Color3f(0.90f, 0.95f, 1.0f));
        background.setApplicationBounds(bounds);
        root.addChild(background);

        // Ambient light.
        AmbientLight ambientLight =
                new AmbientLight(new Color3f(0.4f, 0.4f, 0.4f));
        ambientLight.setInfluencingBounds(bounds);
        root.addChild(ambientLight);

        // Directional light.
        DirectionalLight directionalLight =
                new DirectionalLight(
                        new Color3f(1.0f, 1.0f, 1.0f),
                        new Vector3f(-1.0f, -1.0f, -1.0f)
                );

        directionalLight.setInfluencingBounds(bounds);
        root.addChild(directionalLight);

        // Create the ground appearance.
        Appearance groundAppearance = new Appearance();
        Material groundMaterial = new Material();
        groundMaterial.setDiffuseColor(new Color3f(0.2f, 0.7f, 0.3f));
        groundAppearance.setMaterial(groundMaterial);

        // Create the ground.
        Box ground = new Box(1.3f, 0.02f, 0.6f, groundAppearance);

        Transform3D groundMove = new Transform3D();
        groundMove.setTranslation(new Vector3f(0.0f, GROUND_Y - 0.03f, 0.0f));

        TransformGroup groundTrans = new TransformGroup(groundMove);
        groundTrans.addChild(ground);
        root.addChild(groundTrans);

        // Create the ball appearance.
        Appearance ballAppearance = new Appearance();
        Material ballMaterial = new Material();
        ballMaterial.setDiffuseColor(new Color3f(0.9f, 0.1f, 0.1f));
        ballAppearance.setMaterial(ballMaterial);

        // Create the ball.
        Sphere ball = new Sphere(
                BALL_RADIUS,
                Sphere.GENERATE_NORMALS,
                40,
                ballAppearance
        );

        // Create a TransformGroup for the ball.
        ballTrans = new TransformGroup();

        // Important:
        // This allows Java 3D to change the ball's position after it is drawn.
        ballTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Add the ball to the TransformGroup.
        ballTrans.addChild(ball);

        // Add the TransformGroup to the scene.
        root.addChild(ballTrans);

        root.compile();

        return root;
    }

    private void animateBall() {

        if (paused) {
            return;
        }

        // Increase phase to continue the animation.
        phase = phase + speed;

        // Create bouncing motion.
        double bounce = Math.abs(Math.sin(phase));

        // Calculate vertical position.
        float yPosition =
                GROUND_Y + BALL_RADIUS + (float) (bounce * BOUNCE_HEIGHT);

        // Squash the ball when it is close to the ground.
        double squashAmount = 0.0;

        if (bounce < 0.15) {
            squashAmount = (0.15 - bounce) / 0.15;
        }

        double scaleX = 1.0 + (0.25 * squashAmount);
        double scaleY = 1.0 - (0.35 * squashAmount);
        double scaleZ = 1.0 + (0.25 * squashAmount);

        // Create scaling transform.
        Transform3D scale = new Transform3D();
        scale.setScale(new Vector3d(scaleX, scaleY, scaleZ));

        // Create movement transform.
        Transform3D move = new Transform3D();
        move.setTranslation(new Vector3f((float) xPosition, yPosition, 0.0f));

        // Combine movement and scaling.
        move.mul(scale);

        // Apply movement and squash effect to the ball.
        ballTrans.setTransform(move);
    }

    private void addKeyboardControls(Canvas3D canvas) {

        canvas.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                if (key == KeyEvent.VK_SPACE) {
                    paused = !paused;
                }

                if (key == KeyEvent.VK_LEFT) {
                    xPosition = xPosition - 0.08;
                }

                if (key == KeyEvent.VK_RIGHT) {
                    xPosition = xPosition + 0.08;
                }

                if (key == KeyEvent.VK_UP) {
                    speed = speed + 0.01;
                }

                if (key == KeyEvent.VK_DOWN) {
                    speed = speed - 0.01;

                    if (speed < 0.01) {
                        speed = 0.01;
                    }
                }
            }
        });
    }

    public static void main(String[] args) {

        BouncingBall3D window = new BouncingBall3D();
        window.setVisible(true);
    }
}
