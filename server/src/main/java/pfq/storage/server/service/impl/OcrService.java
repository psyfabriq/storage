package pfq.storage.server.service.impl;

import java.io.File;

import javax.annotation.PreDestroy;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import pfq.storage.server.model.TmpFile;

@Component
@RequestScope
public class OcrService {

	private final tesseract.TessBaseAPI api;
	private final TmpFile tmpFile;
	
	 public OcrService(@Value("${tessdata.dir:classpath:/tessdata}") File tessdata,
             @Value("${tessdata.lang:eng+rus}") String lang,
             TmpFile tmpFile) {
      this.tmpFile = tmpFile;
      this.api = new tesseract.TessBaseAPI();
      String path = tessdata.toPath().toString();
    //  log.debug("Load tessdata from {} (lang={}).", path, lang);
      if (this.api.Init(path, lang) != 0) {
          throw new IllegalStateException("Could not initialize tesseract.");
      }
  }

  public String read(MultipartFile multipartFile) {
      File tmp = this.tmpFile.write(multipartFile).asFile();
      return this.read(tmp);
  }

  public String read(File file) {
      BytePointer outText = null;
      lept.PIX image = null;
      try {
          image = lept.pixRead(file.getAbsolutePath());
          this.api.SetImage(image);
          // Get OCR result
          outText = this.api.GetUTF8Text();
          return outText == null ? "" : outText.getString();
      } finally {
          if (outText != null) {
              outText.deallocate();
          }
          if (image != null) {
              lept.pixDestroy(image);
          }
      }
  }

  @PreDestroy
  void cleanup() {
     // log.debug("Cleanup TessBaseAPI.");
      this.api.End();
  }

}
