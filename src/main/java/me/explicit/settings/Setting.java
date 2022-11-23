package me.explicit.settings;

import java.util.ArrayList;

import me.explicit.config.ConfigManager;
import me.explicit.module.Module;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit him
 *
 *  @author HeroCode
 */
public class Setting {
	
	private String name;
	private Module parent;
	private String mode;
	
	private String sval;
	private ArrayList<String> options;
	
	private boolean bval;
	
	private double dval;
	private double min;
	private double max;
	private boolean onlyint = false;
	
    public boolean percentage = false;
	private boolean visible = false;
	

	public Setting(String name, Module parent, String sval, ArrayList<String> options){
		this.name = name;
		this.parent = parent;
		this.sval = sval;
		this.options = options;
        this.mode = "Combo";
        this.visible = false;
    }

	public Setting(String name, Module parent, boolean bval){
		this.name = name;
		this.parent = parent;
		this.bval = bval;
        this.mode = "Check";
        this.visible = false;
    }

    public Setting(String string, Module module, double d, double d2, double d3, boolean bl) {
        this.name = string;
        this.parent = module;
        this.dval = d;
        this.min = d2;
        this.max = d3;
        this.onlyint = bl;
        this.mode = "Slider";
        this.visible = false;
    }

    public Setting(String string, Module module, double d, double d2, double d3, boolean bl, boolean bl2) {
        this.name = string;
        this.parent = module;
        this.dval = d;
        this.min = d2;
        this.max = d3;
        this.onlyint = bl;
        this.percentage = bl2;
        this.mode = "Slider";
        this.visible = false;
    }
	
    public void setVisible(boolean flag) {
        this.visible = !flag;
    }

    public boolean isVisible() {
        return this.visible;
    }
	
	public String getName(){
		return name;
	}
	
	public Module getParentMod(){
		return parent;
	}
	
	public String getValString(){
		return this.sval;
	}
	
    public void setValString(String string) {
        if (this.sval != string) {
            this.sval = string;
            ConfigManager.SaveConfigFile("Default");
        }
    }
    
    public void setValStringNoSave(String s) {
        this.sval = s;
    }
    
	public ArrayList<String> getOptions(){
		return this.options;
	}
	
	public boolean isEnabled(){
		return this.bval;
	}
	
	public boolean getValBoolean(){
		return this.bval;
	}
	
    public void setValBoolean(boolean bl) {
        if (this.bval != bl) {
            this.bval = bl;
            ConfigManager.SaveConfigFile("Default");
        }
    }

    public int getValInt() {
        if (this.onlyint) {
            this.dval = (int)this.dval;
        }
        return (int)this.dval;
    }
    
    public void setValBooleanNoSave(boolean bl) {
        this.bval = bl;
    }
    
	public double getValDouble(){
		if(this.onlyint){
			this.dval = (int)dval;
		}
		return this.dval;
	}

    public void setValDouble(double d) {
        if (this.dval != d) {
            this.dval = d;
            ConfigManager.SaveConfigFile("Default");
        }
    }
    
    public void setValDoubleNoSave(double d) {
        this.dval = d;
    }
    
	public double getMin(){
		return this.min;
	}
	
	public double getMax(){
		return this.max;
	}
	
	public boolean isCombo(){
		return this.mode.equalsIgnoreCase("Combo") ? true : false;
	}
	
	public boolean isCheck(){
		return this.mode.equalsIgnoreCase("Check") ? true : false;
	}
	
	public boolean isSlider(){
		return this.mode.equalsIgnoreCase("Slider") ? true : false;
	}
	
	public boolean onlyInt(){
		return this.onlyint;
	}

}
