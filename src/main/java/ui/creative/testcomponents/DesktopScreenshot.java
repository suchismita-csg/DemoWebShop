package ui.creative.testcomponents;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class DesktopScreenshot {
	public static void takeReportSnapshot() {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            //Rectangle screenRect = new Rectangle(100,100,500,500);
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            Thread.sleep(6000);
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            
            ImageIO.write(screenCapture, "png", new File("RepostSnapshot.png"));
            System.out.println("Screenshot saved to screenshot.png");
        } catch (Exception e) {
            System.err.println("Error capturing screenshot: " + e.getMessage());
        }
    }
}
