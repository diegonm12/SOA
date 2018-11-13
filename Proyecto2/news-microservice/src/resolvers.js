export default{
    Query:{
        allNews: async (parent, args, {News}) =>{
            const news = await News.find();
            //como el id de mongo no es un string,hay que hacer eso
            return news.map(x =>{
                x._id = x._id.toString();
                return x;
            })            
        }
    },
    Mutation:{
        createNews: async(parent,args, {News}) =>{
            const news = await new News(args).save();
            news._id = news._id.toString();
            return news;     
        }

    }
}