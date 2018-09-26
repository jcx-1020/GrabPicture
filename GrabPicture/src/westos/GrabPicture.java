package westos;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 抓取照片
 *
 * @author 姜晨星
 */
public class GrabPicture {
    public static void main(String[] args) {
        //定义Connection、PreparedStatement、ResultSet分别为空
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //定义一个ArrayList用来储存从数据库读取的img链接
        ArrayList<String> imgs = new ArrayList<>();
        try {
            //使用Utils工具类中getConnection()方法创建Connection
            conn = Utils.getConnection();
            //从数据库中product表中获取列为img的数据
            stmt = conn.prepareStatement("select img from product");
            //执行sql语句
            rs = stmt.executeQuery();
            //把获取到的数据放到imgs集合中
            while (rs.next()) {
                imgs.add(rs.getString("img"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭
            Utils.close(rs, stmt, conn);
        }
        //遍历imgs集合
        for (String img1 : imgs) {
            try {
                //与https：拼接字符串
                String url = "https:" + img1;
                //定义正则为[/]，并截取放入split数组中
                String[] split = url.split("[/]");
                //获取数组最后一个元素
                String img = split[split.length - 1];
                //判断url长度
                if (url.length() > 31) {
                    //执行url链接
                    URLConnection urlConnection = new URL(url).openConnection();
                    //序列化
                    InputStream is = urlConnection.getInputStream();
                    //反序列化
                    FileOutputStream fos = new FileOutputStream("C:\\Users\\12851\\Desktop\\img1\\" + img);
                    //创建byte数组
                    byte[] bytes = new byte[1024 * 64];
                    while (true) {
                        //获取序列化的字节个数
                        int len = is.read(bytes);
                        //结束条件
                        if (len == -1) {
                            break;
                        }
                        //获取的字节写入数组
                        fos.write(bytes, 0, len);
                    }
                    //关闭
                    is.close();
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //如果链接错误跳出此次循环，执行下一次
                continue;
            }
        }

    }
}
