namespace Platform.Context
{
    public interface IContextRepository
    {
        IContext GetContext();
        void SetContext(IContext context);
    }
}
