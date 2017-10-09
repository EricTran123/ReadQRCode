package ReadQRCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class ReadQRCode {
    public static String readQRCode (String filePath) throws FileNotFoundException, IOException, NotFoundException{
        Hashtable<DecodeHintType, Object> hintMap = new Hashtable<DecodeHintType, Object>();
        hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new FileInputStream(filePath)))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
                hintMap);
        return qrCodeResult.getText();
    }
}
