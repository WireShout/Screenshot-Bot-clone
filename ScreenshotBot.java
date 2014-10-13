import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/*****
 * 
 * @author Ankit Bhandary
 * @contributors Marc Estevadeordal, Matthew Chou, Rebecca Choi
 *This program and it's contents are open to the public to alter,though 
 *I ask that if you do alter it, please give credit to me Ankit Bhandary,
 *The original creator.
 *
 */

public class ScreenshotBot {

	private static int interval;
	private static Stack superStack = new Stack();

	public static void stacker(String arg) {
		superStack.push(arg);
	}

	public static void zipThemUp() throws IOException {
		DateFormat df = new SimpleDateFormat("HH_mm_ss");
		Date dateobj = new Date();
		FileOutputStream output = new FileOutputStream("ZippedPics"+
		df.format(dateobj)+".zip");
		ZipOutputStream zipper = new ZipOutputStream(output);

		while (superStack.isEmpty() == false)
			zipFile(superStack.pop().toString(), zipper);
		zipper.close();
		output.close();
	}

	public static void zipFile(String fileNam, ZipOutputStream zos)
			throws IOException {
		File file = new File(fileNam);
		FileInputStream stream = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileNam);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = stream.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		stream.close();
	}

	public static void main(String[] args) throws AWTException, IOException {
		Robot robo = new Robot();
		int diff = 0;
		int length = 0;
		int change = 0;
		int max = 0;
		int counter = 0;
		int steam = 0;
		JOptionPane
				.showMessageDialog(
						null,
						"Make sure this file is in a new folder,"
								+ " otherwise there will be alot of pictures, "
								+ "\nalso the game should run in borderless."
								+ "\n -Programmed by Ankit Bhandary");
		String[] options = { "Interval in seconds", "Interval in minutes",
				"End" };
		int choice = JOptionPane.showOptionDialog(null, "Choose an Option",
				null, diff, diff, null, options, options);

		if (choice == 0) {
			change = Integer
					.parseInt(JOptionPane
							.showInputDialog("Enter how often (in seconds) you want a screenshot"));
			interval = (change * 1000);
			JOptionPane
			.showMessageDialog(
					null,
					"The intervale is now"
							+ (interval / 1000)
							/ 60
							+ " screenshots per second, if it's higher than or equal to 60 seconds or"
							+ "\nequal to 0 seconds or lower i'll change the interval to 59 seconds and 1 "
							+ "\nsecond respectively");
			if (interval > 60000)
				interval = 58000;
			if (interval < 0)
				interval = 2000;
			change = Integer
					.parseInt(JOptionPane
							.showInputDialog("Enter how long you want to take a screenshot in seconds "
									+ "\nin relation to interval, this number over the interval produces that "
									+ "\nnumber of screenshots ie if this number is 10 and interval is 2 it is 10/2 "
									+ "\nthis 5 screenshots")) * 1000;
			max = change / interval;
			System.out.println(change);
		}
		if (choice == 1) {
			change = Integer
					.parseInt(JOptionPane
							.showInputDialog("Enter how often in minutes you want a screenshot")) * 60;
			interval = (change * 1000);
			JOptionPane
					.showMessageDialog(
							null,
							"The intervale is now"
									+ (interval / 1000)
									/ 60
									+ " screenshots per second, if it's higher than or equal to 60 seconds or"
									+ "\nequal to 0 seconds or lower i'll change the interval to 59 seconds and 1 "
									+ "\nsecond respectively");
			if (interval > 60000)
				interval = 58000;
			if (interval < 0)
				interval = 2000;
			change = Integer
					.parseInt(JOptionPane
							.showInputDialog("Enter how long you want to take a screenshot in seconds "
									+ "\nin relation to interval, this number over the interval produces that "
									+ "\nnumber of screenshots ie if this number is 10 and interval is 2 it is 10/2 "
									+ "\nthis 5 screenshots")) * 1000;
			max = change / interval;
		}
		if (choice == 2)
			System.exit(0);

		steam = JOptionPane.showConfirmDialog(null,
				"Do you want to use Steam screenshot method?");
		System.out.println(steam);
		int count = 0;
		if (steam == 1)
			while (counter < max) {

				Rectangle screenRect = new Rectangle(Toolkit
						.getDefaultToolkit().getScreenSize());
				BufferedImage capture = new Robot()
						.createScreenCapture(screenRect);
				File img = new File(count + ".png");
				stacker(count + ".png");
				ImageIO.write(capture, "png", img);
				count++;
				robo.delay(interval);
				counter++;

			}
		else if (steam == 0)
			while (counter < max) {
				robo.keyPress(KeyEvent.VK_F12);
				robo.keyRelease(KeyEvent.VK_F12);
				robo.delay(interval);
				counter++;
			}
		JOptionPane
				.showMessageDialog(
						null,
						"The Screenshots have been processed, "
								+ "we have "
								+ counter
								+ " screenshots, opening imgur for convienent uploading"
								+ "\n I also zipped up a copy if you used non-steam");

		java.awt.Desktop.getDesktop().browse(
				java.net.URI.create("http://imgur.com"));
			zipThemUp();

	}
}
