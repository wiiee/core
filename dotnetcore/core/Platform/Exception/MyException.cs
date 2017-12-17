namespace Platform.Exception
{
    using System;

    public class MyException : Exception
    {
        public string AppId { get; set; }

        public MyException(string appId, string message) : base(message)
        {
            this.AppId = AppId;
        }

        public MyException(string appId, string message, Exception innerException) : base(message, innerException)
        {
            this.AppId = AppId;
        }
    }
}
