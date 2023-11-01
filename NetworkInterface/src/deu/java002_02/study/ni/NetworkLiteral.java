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

	// NOTE: 외부에서 이 유형의 객체를 생성할 수 없습니다.
	private NetworkLiteral()
	{
		
	}
}
