package com.documentmatrix.core

/**
 * Created by root on 25/12/15.
 */
sealed abstract class BST[T](comparison:(T,T)=>Int) {
  class Node(var data:T,var left:Node,var right:Node)

   var root:Node=null;

/*  def +(data: T): Any = {
    def insert(optTree: OptBst): BST = optTree match {
      case None => BST(data)(comparison)
      case Some(tree) => comparison(data, tree.data) match {
        case cmp if cmp < 0 => tree(left = Some(insert(tree.left)))
        case cmp if cmp > 0 => tree(right = Some(insert(tree.right)))
        case _ => tree
      }
    }
    insert(Some(this))
  }*/
}
