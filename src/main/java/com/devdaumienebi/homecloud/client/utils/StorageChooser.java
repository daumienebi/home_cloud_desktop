package com.devdaumienebi.homecloud.client.utils;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StorageChooser {
	
	/**
	 * 
	 * @param 
	 * @return - Returns the selected path
	 */
	
	public String setStoragePath() {
		System.out.print("hey");
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Select the storage location");
		String storagePath = "";
		//set the default location to the desktop
		String dirEsc = System.getProperty("user.home");
		jfc.setCurrentDirectory(new File(dirEsc + "/Desktop"));
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		int option = jfc.showOpenDialog(jfc);
		if(option == JFileChooser.APPROVE_OPTION) {
			storagePath = jfc.getSelectedFile().getAbsolutePath();
		}		
		System.out.print(storagePath);
		return storagePath;
	}
}
