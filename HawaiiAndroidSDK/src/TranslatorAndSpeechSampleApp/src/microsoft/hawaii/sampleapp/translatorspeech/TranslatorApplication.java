/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.translatorspeech;

import java.util.ArrayList;
import java.util.List;

import microsoft.hawaii.sampleappbase.HawaiiBaseApplication;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.Language;

/**
 *
 */
public class TranslatorApplication extends HawaiiBaseApplication {
	private List<String> translateLanguageList;
	private List<String> speechLanguageList;
	private List<String> grammarList;

	public List<String> getTranslateLanguageList() {
		return this.translateLanguageList;
	}

	public void setTranslateLanguageList(List<String> list) {
		this.translateLanguageList = list;
	}

	public void setTranslateLanguageList(Language[] list) {
		if (list != null) {
			List<String> languageList = new ArrayList<String>();
			for (Language l : list) {
				languageList.add(l.getCode());
			}

			this.setTranslateLanguageList(languageList);
		}
	}

	public List<String> getSpeechLanguageList() {
		return this.speechLanguageList;
	}

	public void setSpeechLanguageList(List<String> list) {
		this.speechLanguageList = list;
	}

	public void setSpeechLanguageList(Language[] list) {
		if (list != null) {
			List<String> languageList = new ArrayList<String>();
			for (Language l : list) {
				languageList.add(l.getCode());
			}

			this.setSpeechLanguageList(languageList);
		}
	}

	public List<String> getGrammarList() {
		return this.grammarList;
	}

	public void setGrammarList(List<String> list) {
		this.grammarList = list;
	}
}
