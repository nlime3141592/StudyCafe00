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

            // 2) Read: (�̿��� ���� ��ȣ, �г���, ������Ʈ ���� ��¥) x N��
            while (true)
            {
                String line = m_netModule.readLine();

                // Check for null, indicating a disconnected server
                if (line == null)
                {
                    System.out.println("���� ������ ������ϴ�.");
                    return false;
                }

                if (line.equals(NetworkLiteral.EOF))
                	break;

                lines.add(line);
            }

            // 3) Read: ��� ���
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
            // ��� ����, N > 0
            System.out.println("��� ����");
            break;
        case "<FAILURE>":
            // ��� ����, N == 0
            System.out.println("��� ����, N == 0");
            break;
        case "<ERROR>":
            // ��� ����
            System.out.println("��� ����");
            break;
        // ����
        default:
            System.out.println("�˼�����");
            break;
        }
    }

    @Override
    public void bindNetworkModule(INetworkModule _netModule)
    {
        m_netModule = _netModule;
    }
}
