namespace Entity.History
{
    using System;

    public class HistoryInfo
    {
        public string Data;
        public string UserId;
        public DateTime Date;

        public HistoryType Type;

        public HistoryInfo(string data, String userId, DateTime date, HistoryType type)
        {
            this.Data = data;
            this.UserId = userId;
            this.Date = date;
            this.Type = type;
        }
    }
}
