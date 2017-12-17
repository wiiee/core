namespace Platform.Data
{
    public interface IUser
    {
        string Id { get; }
        string Password { get; set; }
    }
}
