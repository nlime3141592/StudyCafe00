package deu.java002_02.study.ni;

import deu.java002_02.study.main.IService;

public interface IConnectionService extends IService
{
	void bindConnectionModule(ConnectionModule _conModule);
	boolean isConnectionEnded();
}
