namespace Platform.Data
{
    using System.Collections.Generic;

    public class History : BaseEntity
    {
        //Id为[ClassName]_[Id]

        public List<HistoryInfo> HistoryInfos;

        public History(string id, List<HistoryInfo> historyInfos)
        {
            this.Id = id;
            this.HistoryInfos = historyInfos;
        }
    }
}
