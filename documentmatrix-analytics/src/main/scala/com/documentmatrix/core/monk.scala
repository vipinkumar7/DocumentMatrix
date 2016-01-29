package com.documentmatrix.core


import org.junit.{Test, Before,Assert}

import scala.collection.mutable

/**
 * Created by root on 25/12/15.
 */


class  BstMap[K,V](comp:(K,K)=>Int) extends  mutable.Map[K,V]{

  class Node(var key:K,var value:V,var left:Node,var right:Node)

  private var root:Node=null;

  def +=(kv: (K, V))= {
    val(key,value)=kv
    def recur(n:Node):Node={
      if (n==null)new Node(key,value ,null,null)
      else{
        val c=comp(key,n.key)
        if(c==0)
          n.value=value;
        else if(c<0)
          n.left=recur(n.left)
        else
          n.right=recur(n.right)
        n
      }
    }
    root=recur(root);
    this
  }

   def -=(key: K)= {
    this
   }

   def get(key: K): Option[V]={

     var rover=root
     var c = if (rover!=null) comp(key,rover.key) else 0
     while(rover!=null&& c!=0){
       rover =if(c<0) rover.left else rover.right
       c = if (rover!=null) comp(key,rover.key) else 0
     }
     if(rover==null)None else Some(rover.value)
   }

   def iterator= new Iterator[(K, V)]{

     def next=null;
     def hasNext=false;
   }

}

object monk{

  def main(args: Array[String]) {

    var map:BstMap[Int,Int]=null;

    @Before def makeMap: Unit ={
      map=new BstMap[Int,Int]((k1,k2)=>k1.compareTo(k2))
    }

    @Test def addGet{

      map +=5->5
      Assert.assertEquals(Some(5),map.get(5))
    }
  }
}