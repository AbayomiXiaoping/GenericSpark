package learning

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
object MyApp{

   def main(args: Array[String]): Unit = {
     println("Testing App")

     val spark:SparkSession = SparkSession.builder()
       .appName("App")
       .master("local")
       .getOrCreate()

     import spark.implicits._

     val depDetail = List(
       (1,"IT"),
       (2,"CIVIL"),
       (3,"MECH"),
       (4,"HR")
     ).toDF("depID","deptName")

     val personDF = List(
       (1,"Sazzad",200,"2013-10-12",1),
       (2,"Rameah",300,"2010-10-12",2),
       (3,"Haresh",400,"2016-10-12",1),
       (4,"Jonny",500,"2015-10-12",2),
       (5,"Mitali",400,"2018-10-12",3),
       (6,"Amit",400,"2012-10-12",3),
       (7,"Omkar",100,"2010-10-12",3),
       (8,"Smith",500,"2010-10-12",4),
       (9,"Sanket",100,"2010-10-12",2),
       (10,"Prinkya",400,"2018-10-12",1),
       (11,"Puni",600,"2016-10-12",4),
       (12,"Pual",600,"2015-10-12",2),
       (13,"Will",100,"2014-10-12",3),
     ).toDF("ID","Name","Salary","DOJ","DeptId")

     println(personDF.schema.treeString)

     val getDataWithSalary: DataFrame=>DataFrame = {
       df => {
         df.filter(col("Salary")>=500)
       }
     }
     def getDeptDetails(deptDF:DataFrame,pDF:DataFrame) :DataFrame = {

       pDF.join(
         deptDF,
         col("DeptID") === col("id"),"left"
       )

     }
     getDeptDetails(depDetail,personDF).show()
     val transform: DataFrame => DataFrame = List[DataFrame=>DataFrame](
       getDataWithSalary(_),
       getDeptDetails(depDetail,_)
     ).reduce(_ andThen _)

     transform(personDF).show(false)
   }

}
