package my.loong.exceptions;

/**
 * @program: CreateMaterial
 * @description:
 * @AUTHOR: tlw
 * @create: 2018-11-20 20:48
 */
public class ArgumentOutOfRangeException extends Exception{
    public ArgumentOutOfRangeException() {
        super();
    }

    public ArgumentOutOfRangeException(String message) {
        super(message);
    }

    public ArgumentOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentOutOfRangeException(Throwable cause) {
        super(cause);
    }

    protected ArgumentOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}