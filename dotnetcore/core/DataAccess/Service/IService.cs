namespace DataAccess.Service
{
    using Platform.Context;

    public interface IService
    {
        IContext GetContext();
    }
}