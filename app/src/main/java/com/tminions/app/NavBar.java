package com.tminions.app;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class NavBar {
	
	public MenuBar GetNavbar () {
	    MenuBar menubar = new MenuBar();
	    
	    Menu uploadMenu = new Menu("Upload Data");
	    this.onAction(uploadMenu, "dataUploadScreen");
	    menubar.getMenus().add(uploadMenu);
	    
	    Menu lastMenu = new Menu("Last Uploaded");
	    this.onAction(lastMenu, "organizationLastUploadedScreen");
	    menubar.getMenus().add(lastMenu);
	    
	    Menu reportMenu = new Menu("Reports");
	    this.onAction(reportMenu, "reportGenerationScreen");
	    menubar.getMenus().add(reportMenu);
	    
	    Menu customReportMenu = new Menu("Custom Reports");
	    this.onAction(customReportMenu, "customReportGenerationScreen");
	    menubar.getMenus().add(customReportMenu);
	    
	    Menu conflictMenu = new Menu("Conflicts");
	    this.onAction(conflictMenu, "conflictsScreen");
	    menubar.getMenus().add(conflictMenu);
		return menubar;
	}
	
	public void onAction(Menu menu, String screenName)
	{	
	    MenuItem menuItem = new MenuItem();

	    menu.getItems().add(menuItem);
	    menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
	    menu.addEventHandler(Menu.ON_SHOWING, event -> {
	    	SceneController.getSceneController().switchToScene(screenName, true);	    	
	    });
	}

}
