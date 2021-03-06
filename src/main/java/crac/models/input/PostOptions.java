package crac.models.input;

public class PostOptions {

	private int id;
	private int importanceLevel;
	private int neededProficiencyLevel;
	private int proficiencyValue;
	private int importanceValue;
	private int likeValue;
	private boolean mandatory;
	private String text;

	public PostOptions() {
	}

	public int getProficiencyValue() {
		return proficiencyValue;
	}

	public void setProficiencyValue(int proficiencyValue) {
		this.proficiencyValue = proficiencyValue;
	}

	public int getLikeValue() {
		return likeValue;
	}

	public void setLikeValue(int likeValue) {
		this.likeValue = likeValue;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public int getImportanceValue() {
		return importanceValue;
	}

	public void setImportanceValue(int importanceValue) {
		this.importanceValue = importanceValue;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getImportanceLevel() {
		return importanceLevel;
	}

	public void setImportanceLevel(int importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	public int getNeededProficiencyLevel() {
		return neededProficiencyLevel;
	}

	public void setNeededProficiencyLevel(int neededProficiencyLevel) {
		this.neededProficiencyLevel = neededProficiencyLevel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
