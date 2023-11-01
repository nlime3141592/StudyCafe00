package deu.java002_02.study.ni;

public final class NetworkLiteral
{
	public final static String EOF = "<EOF>";
	public final static String ERROR = "<ERROR>";
	
	public static boolean isTrailer(String _line)
	{
		switch(_line)
		{
		case NetworkLiteral.EOF:
		case NetworkLiteral.ERROR:
			return true;
		default:
			return false;
		}
	}

	// NOTE: �ܺο��� �� ������ ��ü�� ������ �� �����ϴ�.
	private NetworkLiteral()
	{
		
	}
}
