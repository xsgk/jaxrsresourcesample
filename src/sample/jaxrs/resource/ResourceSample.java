package sample.jaxrs.resource;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.HashMap;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import sample.jaxrs.resource.util.Log;

@Path("/resourcesample")
public class ResourceSample {

	private final String JSON_REQUESTID = "requestid";
	private final String JSON_UPLOADDAT = "uploaddat";
	private final String JSON_DOWNLOADDAT = "downloaddat";

	private final String SAVENAME_PATTERN = "yyyymmddhhMMss";

	private final String UPLOAD_FILEPATH = "/opt/jaxrsresourcesample/upload";
	private final String DOWNLOAD_FILEPATH = "/opt/jaxrsresourcesample/download/download.png";


	/*--------------------------------------------------*/
	/**
	 * POSTリクエストに含まれるアップロードデータを保存、ダウンロード用データを応答する。
	 * @param reqjson
	 * @return
	 * @throws IOException
	 */
	/*--------------------------------------------------*/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> respond(JsonObject reqjson)
			throws IOException {

		Log.out(reqjson.toString());
		String uploaddat = reqjson.getString(JSON_UPLOADDAT);
		saveuploaddat(uploaddat);

		// レスポンスJSONの作成と応答
		HashMap<String, String> resjson = new HashMap<>();
		resjson.put(JSON_REQUESTID, reqjson.getString(JSON_REQUESTID));
		resjson.put(JSON_DOWNLOADDAT, getFileData());
		return resjson;
	}

	/*--------------------------------------------------*/
	/**
	 * Base64エンコードされたデータを元にファイル作成・保存
	 * @param base64encdat
	 * @throws IOException
	 */
	/*--------------------------------------------------*/
	private void saveuploaddat(String base64encdat) throws IOException {
		// 応答に含まれるバイナリデータをファイルに保存
		Decoder decoder = Base64.getUrlDecoder();
		byte[] binary = decoder.decode(base64encdat);
		FileOutputStream fos = new FileOutputStream(UPLOAD_FILEPATH + "/" + getSaveFileName() + ".png");
		fos.write(binary);
		fos.close();
	}

	/*--------------------------------------------------*/
	/**
	 * ダウンロードデータをBase64エンコードされた文字列として取得
	 * @param path
	 * @return
	 */
	/*--------------------------------------------------*/
	public String getFileData() {

		String encodeddata = null;

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			FileInputStream fis = new FileInputStream(DOWNLOAD_FILEPATH);
			int b = 0;
			while ((b = fis.read()) != -1) {
				os.write(b);
			}
			byte[] binary = os.toByteArray();

			Encoder encoder = Base64.getUrlEncoder();
			encodeddata = encoder.encodeToString(binary);
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return encodeddata;
	}

	/*--------------------------------------------------*/
	/**
	 * アップロードデータの保存ファイル名を生成する
	 * @return 保存ファイル名
	 */
	/*--------------------------------------------------*/
	private String getSaveFileName() {
		SimpleDateFormat df = new SimpleDateFormat(SAVENAME_PATTERN);
		return df.format(new Date());
	}

}