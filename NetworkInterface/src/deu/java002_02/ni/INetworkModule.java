package deu.java002_02.ni;

public interface INetworkModule
{
	String readLine();
	int readByte();

	boolean writeLine(String _message);
	boolean writeByte(int _byte);
}