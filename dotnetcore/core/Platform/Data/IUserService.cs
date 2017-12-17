namespace Platform.Data
{
    using Platform.Data;

    public interface IUserService
    {
        IUser Get(string id);
        ServiceResult LogIn(string id, string password);
        ServiceResult SignUp(IUser user);
    }
}
