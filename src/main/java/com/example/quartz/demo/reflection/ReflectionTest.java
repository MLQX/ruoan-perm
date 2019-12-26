package com.example.quartz.demo.reflection;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {

    public static void main(String[] args) {

//        getInstanceByClz();
//        getConstructorByClz();
        getMethodByClz();
//        getFieldByClz();
//        getParentClassOrInterfaceByClz();
    }

    /**
     * 通过反射创建实例
     */
    public static void getInstanceByClz(){
        Person p = new Person();
        Person p2 = null;
//        Class<?> cls = p.getClass();
//        Class<?> cls = Person.class;
        try {
            Class<?> cls = Class.forName("com.example.quartz.demo.reflection.Person");
            p2 =  (Person)cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(p);
        System.out.println(p2);
    }

    /**
     * 通过反射获取 所有的构造方法，单个的构造方法
     */
    public static void getConstructorByClz(){

        class P{
            public P(){
                System.out.println("无参构造方法");
            }
            private P(int a){
                System.out.println("有参构造方法"+a);
            }
        }

        P p = new P();
        Class<?> clz = p.getClass();
        //获取所有构造方法
        Constructor[] constructors = clz.getConstructors();
//        for (int i = 0; i < constructors.length; i++) {
//            Constructor c = constructors[i];
//            c.get
//        }
        try {
            //获取带有指定参数类型的构造器
            Constructor c = clz.getDeclaredConstructor(int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过反射获取方法的相关信息
     */
    public static void getMethodByClz(){
        class P{
            public P(){
//                System.out.println("无参构造方法");
            }

            public String run(){
                System.out.println("run");
                return "125";
            }
            private void run3(){
                System.out.println("run3");
            }
            public void run2(){
                System.out.println("run2");
            }

        }
        P p = new P();
        Class<?> clz=p.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            m.setAccessible(true);
            try {
                m.invoke(p); // 调用方法
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //获取方法的返回类型
//            Class<?> returnType = m.getReturnType();  //返回java.lang.String
//            System.out.println(returnType.getName());
//            //获取方法的全部参数
//            Class<?>[] parameterTypes = m.getParameterTypes();
//            for (int j = 0; j < parameterTypes.length; j++) {
//                if (parameterTypes[j].equals("java.lang.Integer")) {
//                    System.out.println("我有一个参数是Integer哦");
//
//                }
//            }
        }



    }


    /**
     * 通过反射获取成员的相关信息
     */
    public static void getFieldByClz(){
        class P{
            private int a;
            public String b;
        }
        P p = new P();
        Class<?> clz=p.getClass();
        Field[] fields = clz.getDeclaredFields();
        try {
            Field detailedField = clz.getDeclaredField("a");
//            System.out.println(detailedField.getAnnotations()[0].toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }


    /**
     * 通过反射获取父类和父接口的相关信息
     */
    public static void getParentClassOrInterfaceByClz(){
        class T{

        }
        class P extends T implements Serializable {
            private static final long serialVersionUID = -7010829176738569866L;
            private int a;
            public String b;
        }
        P p = new P();
        Class<?> clz=p.getClass();
        System.out.println(clz.getSuperclass());
        Class<?>[] interfaces = clz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {

            System.out.println(interfaces[i].getName());
        }
    }

}
