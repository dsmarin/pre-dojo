package br.com.amil.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.amil.exception.MatchException;
import br.com.amil.service.GameMatchService;

@Controller
public class GameRaninkingController {

	public static String ROOT = "upload-dir";

	private final GameMatchService service;

	@Autowired
	public GameRaninkingController(GameMatchService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String provideUploadInfo(Model model) throws IOException {

		model.addAttribute("match", service.getMatch());

		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/")
	public String handleGameLogUpload(@RequestParam("file") MultipartFile file, Model model) {

		if (!file.isEmpty()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

				String matchLog = "";

				while (br.ready()) {
					if (matchLog.isEmpty()) {
						matchLog = br.readLine();
					} else {
						matchLog += "\n" + br.readLine();
					}
				}

				br.close();

				model.addAttribute("match", service.loadMatch(matchLog));
			} catch (IOException | RuntimeException | MatchException e) {
				e.printStackTrace();
				model.addAttribute("message", "Failued to upload log file: " + file.getOriginalFilename() + "!");
			}
		} else {
			model.addAttribute("message",
					"Failed to upload log file: " + file.getOriginalFilename() + " because it was empty!");
		}

		return "uploadForm";
	}

}
