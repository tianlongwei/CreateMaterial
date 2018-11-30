package my.loong.commons;

/**
 * @program: CreateMaterial
 * @description:增加国标GB_50936_Method进行承载力验算
 * @AUTHOR: tlw
 * @create: 2018-11-30 14:12
 */
public class GB_50936_Method {
    private static double fy;//钢筋屈服强度
    private static double fcu;//混凝土强度
    private static double B;//钢管总长度
    private static double D;//圆形区直径
    private static double t;//钢管厚度

    private static double As;
    private static double Ac;
    private static double Asc;

    private static double epsilon;

    private static double v_b;
    private static double v_c;

    private static double fsc;

    private static double N0;//承载力
    /****************************************************/
    /*******************计算中间参数值*********************/
    /****************************************************/


    public static double getAc(){
        Ac=(B-D)*D+3.14*Math.pow(D/2.0,2);
        return Ac;
    }

    public static double getAs(){
        As=(B-D)*t*2.0+3.14*(Math.pow(D/2.0+t,2)-Math.pow(D/2.0,2));
        return As;
    }

    public static double getAsc(){
        Asc=getAs()+getAc();
        return Asc;
    }

    private static double getEpsilon(){
        epsilon=getAs()*fy/(getAc()*fcu);
        return epsilon;
    }

    private static double getV_b(){
        v_b=0.131*fy/213.0+0.723;
        return v_b;
    }

    private static double getV_c(){
        v_c=-0.070*fcu/14.4+0.026;
        return v_c;
    }

    private static double getFsc(){
        fsc=(1.212+getV_b()*getEpsilon()+getV_c()*Math.pow(getEpsilon(),2))*fcu;
        return fsc;
    }

    private static double getN0(){
        N0=getAsc()*getFsc();
        return N0;
    }

    public static void zhouya_cal(double N){
        System.out.println("Nu:"+getNu());
        if (N<getN0()){
            System.out.println("轴压情况下，承载力满足要求");
            return;
        }
        System.out.println("轴压情况下，承载力不满足要求");
    }

    //计算后将计算参数返回
    public static String all_zhouya2csv(){
        String title="B,"+"D,"+"t,"+"fy,"+"fcu,"+"As,"+"Ac,"+"Asc,"+"ξ,"+"v_b,"+"v_c,"+"fsc,"+"N0\r\n";
        title+=String.valueOf(B)+","+
                String.valueOf(D)+","+
                String.valueOf(t)+","+
                String.valueOf(fy)+","+
                String.valueOf(fcu)+","+
                String.valueOf(As)+","+
                String.valueOf(Ac)+","+
                String.valueOf(Asc)+","+
                String.valueOf(epsilon)+","+
                String.valueOf(v_b)+","+
                String.valueOf(v_c)+","+
                String.valueOf(fsc)+","+
                String.valueOf(N0)+"\r\n";
        return title;
    }

    /****************************************************/
    /*******************纯弯的情况下**********************/
    /****************************************************/

    private static double fscy;//
    private static double gamma_m;
    private static double Wsc;
    private static double Mu;

    private static double getFscy(){
        fscy=(1.927-0.16*getEpsilon()+0.571*Math.pow(getEpsilon(),2)-0.096*Math.pow(getEpsilon(),3))*fcu;
        return fscy;
    }

    private static double getGamma_m(){
        gamma_m=1.04*getEpsilon()+0.51*Math.log(getEpsilon()+0.1);
        return gamma_m;
    }

    private static double getWsc(){
        Wsc=(B-D)*Math.pow(D,2)/6.0+(0.0490625*Math.pow(D,4)+(Math.pow(0.212314225*D+(B-D)/2.0,2)*3.14*Math.pow(D/2,2)))/(B/2.0);
        return Wsc;
    }

    public static double getMu(){
        Mu=getGamma_m()*getWsc()*getFscy();
        return Mu;
    }

    public static void chunwan_cal(double M){
        System.out.println("Mu:"+getMu());
        if (M<getMu()){
            System.out.println("在纯弯情况下，承载力满足要求");
            return;
        }
        System.out.println("在纯弯情况下，承载力不满足要求");
    }

    //计算后将计算参数返回
    public static String all_chunwan2csv(){
        String title="As,"+"Ac,"+"ξ,"+"fscy,"+"Wsc,"+"γm,"+"Mu\r\n";
        title+=String.valueOf(As)+","+
                String.valueOf(Ac)+","+
                String.valueOf(epsilon)+","+
                String.valueOf(fscy)+","+
                String.valueOf(Wsc)+","+
                String.valueOf(gamma_m)+","+
                String.valueOf(Mu)+"\r\n";
        return title;
    }

    /****************************************************/
    /*******************压弯的情况下**********************/
    /****************************************************/
    private static double H;//长度
    private static double i;
    private static double lambda_sc;

    private static double x;
    private static double phi;
    private static double Nu;

    private static double kE;
    private static double NE;

    //private static double N;//实际受到的轴压力
    private static double getI(){
        i=Math.sqrt(((B-D)*Math.pow(D,3)/12+ 0.0490625*Math.pow(D,4)+Math.pow(0.212314225*D+(B-D)/2.0,2)*3.14*Math.pow(D/2.0,2))/((B-D)*D+3.14*Math.pow(D/2.0,2) ));
        return i;
    }

    private static double getLambda_sc(){
        lambda_sc=H/getI();
        return lambda_sc;
    }

    private static double getX(){
        x=getLambda_sc()*(0.001*fy+0.781);
        return x;
    }

    private static double getPhi(){
        phi=6.0*Math.pow(10,-6)*Math.pow(getX(),2)-0.0055*getX()+1.0756;
        return phi;
    }

    private static double getNu(){
        Nu=getPhi()*getN0();
        return Nu;
    }

    private static double getkE(){
        kE=-1.6112*fy+1290.6;
        return kE;
    }

    private static double getNE() {
        NE=11.6*kE*fsc*Asc/Math.pow(lambda_sc,2);
        return NE;
    }

    public static void zhouwan_cal(double N,double M){
        double val=N/getNu();
        System.out.println("val:"+val);

        double val1=0;
        if (val>=0.255){
            val1=val+M/(1.5*getMu()*(1-0.4*N/getNE()));
        }else {
            val1=-1*N/(2.17*getNu())+M/(getMu()*(1-0.4*N/getNE()));
        }
        System.out.println("val1:"+val1);

        if (val1<=1){
            System.out.println("构建承载力安全");
        }else {
            System.out.println("构建承载力不安全");
        }
    }





    public static void setH(double h) {
        H = h;
    }

    public static double getFy() {
        return fy;
    }

    public static void setFy(double fy) {
        GB_50936_Method.fy = fy;
    }

    public static double getFcu() {
        return fcu;
    }

    public static void setFcu(double fcu) {
        GB_50936_Method.fcu = fcu;
    }

    public static double getB() {
        return B;
    }

    public static void setB(double b) {
        B = b;
    }

    public static double getD() {
        return D;
    }

    public static void setD(double d) {
        D = d;
    }

    public static double getT() {
        return t;
    }

    public static void setT(double t) {
        GB_50936_Method.t = t;
    }
}