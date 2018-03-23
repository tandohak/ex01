package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	private String innerUploadPath = "resources/upload";

	@Resource(name = "uploadPath")
	private String outUploadPath;

	@RequestMapping(value = "/innerUpload", method = RequestMethod.GET)
	public String innerUploadTest() {
		return "innerUploadForm";
	}

	@RequestMapping(value = "/innerUpload", method = RequestMethod.POST)
	public String innerUploadResult(String text, MultipartFile file, HttpServletRequest request, Model model) {
		logger.info("text : " + text);
		logger.info("file :" + file.getOriginalFilename());

		String root_path = request.getSession().getServletContext().getRealPath("/");
		File dirPath = new File(root_path + "/" + innerUploadPath);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		UUID uid = UUID.randomUUID();// 중복방지를 위한 랜덤값 생성
		String savedName = uid.toString() + "_" + file.getOriginalFilename();

		// 해당 경로에 파일 그릇을 만듬
		File target = new File(root_path + "/" + innerUploadPath, savedName);
		logger.info(target.getAbsolutePath());
		try {
			FileCopyUtils.copy(file.getBytes(), target);

			model.addAttribute("text", "text");
			model.addAttribute("fileName", innerUploadPath + "/" + savedName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "innerUploadResult";
	}

	// innerMultiUpload
	@RequestMapping(value = "/innerMultiUpload", method = RequestMethod.GET)
	public String innerMultiUploadForm() {
		logger.info("innerMultiUploadForm");
		return "innerMultiUploadForm";
	}

	@RequestMapping(value = "/innerMultiUpload", method = RequestMethod.POST)
	public String innerMultiUploadResult(String text, List<MultipartFile> files, HttpServletRequest request,
			Model model) {
		logger.info("innerMultiUpload Result POST");
		logger.info(text);

		String root_path = request.getSession().getServletContext().getRealPath("/");
		File dirPath = new File(root_path + "/" + innerUploadPath);

		List<String> fileList = new ArrayList<String>();

		for (MultipartFile file : files) {
			UUID uid = UUID.randomUUID();// 중복방지를 위한 랜덤값 생성
			String savedName = uid.toString() + "_" + file.getOriginalFilename();
			File target = new File(root_path + "/" + innerUploadPath, savedName);

			try {
				FileCopyUtils.copy(file.getBytes(), target);
				fileList.add(innerUploadPath + "/" + savedName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("text", "text");
		model.addAttribute("fileList", fileList);
		return "innerMultiUploadResult";
	}

	// outUpload
	@RequestMapping(value = "/outUpload", method = RequestMethod.GET)
	public String ountUploadForm() {
		logger.info("out upload FORM - GET");
		logger.info("outUploadPath : " + outUploadPath);
		return "outUploadForm";
	}

	@RequestMapping(value = "/outUpload", method = RequestMethod.POST)
	public String ountUploadFormResult(String text, MultipartFile file, Model model) {
		logger.info("innerMultiUpload Result POST");
		logger.info(text);
		logger.info("file name : " + file.getOriginalFilename());
		logger.info("file size : " + file.getSize());
		logger.info("file contentType : " + file.getContentType());

		File dirPath = new File(outUploadPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		UUID uid = UUID.randomUUID();
		String saveName = uid.toString() + "_" + file.getOriginalFilename();
		File target = new File(outUploadPath, saveName);

		try {
			FileCopyUtils.copy(file.getBytes(), target);

			model.addAttribute("text", text);
			model.addAttribute("filename", saveName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "outUploadResult";
	}

	@RequestMapping(value = "displayFile", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> displayFile(String filename) {
		ResponseEntity<byte[]> entity = null;
		InputStream in = null;
		logger.info("[displayFile]:" + filename);

		try {
			String formatName = filename.substring(filename.lastIndexOf(".") + 1);
			MediaType type = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(type);

			in = new FileInputStream(outUploadPath + "/" + filename);

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@RequestMapping(value = "/uploadDrag", method = RequestMethod.GET)
	public String uploadDragForm() {
		logger.info("[uploadDrag] FORM GET");
		return "uploadDragForm";
	}

	@ResponseBody
	@RequestMapping(value = "/uploadDrag", method = RequestMethod.POST)
	public ResponseEntity<List<String>> uploadDragResult(String test, List<MultipartFile> files) {
		logger.info("[uploadDrag] Result POST");
		logger.info(test);

		ResponseEntity<List<String>> entity = null;
		List<String> list = new ArrayList();
		try {
			for (MultipartFile file : files) {
				File dirPath = new File(outUploadPath);
				if (!dirPath.exists()) {
					dirPath.mkdirs();
				}

				/*
				 * UUID uid = UUID.randomUUID(); String saveName =
				 * uid.toString() + "_" + file.getOriginalFilename(); File
				 * target = new File(outUploadPath, saveName);
				 * FileCopyUtils.copy(file.getBytes(), target);
				 */

				String savedName = UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(),
						file.getBytes());

				list.add(savedName);
			}
			entity = new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} catch (IOException e) {
			entity = new ResponseEntity<List<String>>(list, HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return entity;
	}

	// deleteFile
	@ResponseBody
	@RequestMapping(value = "deleteFile", method = RequestMethod.GET)
	public ResponseEntity<String> deleteFile(String filename) {
		ResponseEntity<String> entity = null;
		try {
			System.gc();
			File file = new File(outUploadPath + filename);
			File file2 = new File(outUploadPath + filename.replaceFirst("s_", ""));
			file2.delete();
			file.delete();
			logger.info("[deleteFile] - original : " + filename.replaceFirst("s_", ""));

			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.OK);
		}

		return entity;
	}

	@RequestMapping(value = "/uploadPreview", method = RequestMethod.GET)
	public String uploadPreviewForm() {
		return "uploadPreviewForm";
	}

	@RequestMapping(value = "/uploadPreview", method = RequestMethod.POST)
	public String uploadPreviewResult(String writer, MultipartFile file, Model model) {
		logger.info("[uploadPreview] POST");
		logger.info("writer --> " + writer);
		logger.info("file --> " + file.getOriginalFilename());

		ResponseEntity<String> entity = null;

		try {
			File dirPath = new File(outUploadPath);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}

			String savedName = UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(), file.getBytes());

			model.addAttribute("writer", writer);
			model.addAttribute("path", savedName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "uploadPreviewResult";
	}

	@RequestMapping(value = "/mutiplePreview", method = RequestMethod.GET)
	public String uploadMutiplePreviewForm() {
		return "uploadMutiplePreviewForm";
	}

	@RequestMapping(value = "/mutiplePreview", method = RequestMethod.POST)
	public String uploadmutiplePreviewResult(String writer, List<MultipartFile> files, Model model) {
		logger.info("[uploadPreview] POST");
		logger.info("writer --> " + writer);
		logger.info("files --> " + files.toString());
		 
		List<String> savedNames = new ArrayList<>();
		for (MultipartFile file : files) { 
			System.out.println(file.getOriginalFilename());  
			try {
				File dirPath = new File(outUploadPath);
				if (!dirPath.exists()) {
					dirPath.mkdirs(); 
				}  

				String savedName = UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(),
						file.getBytes());
				savedNames.add(savedName);
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		}    
		System.out.println(savedNames.toString());
		model.addAttribute("writer", writer); 
		model.addAttribute("paths", savedNames);
		 
		return "uploadMultiplePreviewResult";
	}

}
