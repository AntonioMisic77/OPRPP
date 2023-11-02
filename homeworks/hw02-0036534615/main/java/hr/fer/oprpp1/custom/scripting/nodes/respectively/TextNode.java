package hr.fer.oprpp1.custom.scripting.nodes.respectively;

public class TextNode extends Node {

	private String text;
	
	public TextNode(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
