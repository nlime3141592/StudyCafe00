package deu.java002_02.study.provider.service;

import java.util.Vector;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;
import deu.java002_02.study.provider.gui.BlacklistView;

public class BlacklistSelectService extends ProviderService implements INetworkService
{
    private BlacklistView blacklistView;
    private INetworkModule m_netModule;

    public BlacklistSelectService(BlacklistView blacklistView)
    {
        this.blacklistView = blacklistView;
    }

    @Override
    public boolean tryExecuteService()
    {
        try
        {
            // 1) Write: BLACKLIST_SELECT_SERVICE
            m_netModule.writeLine("BLACKLIST_SELECT_SERVICE");

            Vector<String> lines = new Vector<String>();

            // 2) Read: (이용자 고유 번호, 닉네임, 블랙리스트 등재 날짜) x N개
            while (true)
            {
                String line = m_netModule.readLine();

                // Check for null, indicating a disconnected server
                if (line == null)
                {
                    System.out.println("서버 연결이 끊겼습니다.");
                    return false;
                }

                if (line.equals(NetworkLiteral.EOF))
                	break;

                lines.add(line);
            }

            // 3) Read: 통신 결과
            String result = m_netModule.readLine();

            handleCommunicationResult(result);

            // Check success and add rows
            if ("<SUCCESS>".equals(result))
            {
                for (int i = 0; i < lines.size(); i += 3)
                {
                	int uuid = Integer.parseInt(lines.get(i));
                	String nickname = lines.get(i + 1);
                	String blackedDate = lines.get(i + 2);
                    blacklistView.addRow(uuid, nickname, blackedDate);
                }
            }
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            return false;
        }

        return false;
    }

    private void handleCommunicationResult(String result)
    {
        switch (result)
        {
        case "<SUCCESS>":
            // 통신 성공, N > 0
            System.out.println("통신 성공");
            break;
        case "<FAILURE>":
            // 통신 성공, N == 0
            System.out.println("통신 성공, N == 0");
            break;
        case "<ERROR>":
            // 통신 실패
            System.out.println("통신 실패");
            break;
        // 예외
        default:
            System.out.println("알수없음");
            break;
        }
    }

    @Override
    public void bindNetworkModule(INetworkModule _netModule)
    {
        m_netModule = _netModule;
    }
}
