(require  '[clojure.java.io :as io])

;;把整数转成十六进制字符串
(defn toHexString [num]
	(.toUpperCase (java.lang.Integer/toHexString num)))

;;取魔数校验文件格式
(defn getMagicNumber [in]
	(loop [magic ""
		      counter 4]
		      (if (> counter 0)  
		      	(recur (str magic (toHexString (.readUnsignedByte in))) 
		      		(dec counter))
		      	magic)
		)
)

;;检查文件是否是class文件，前4个字节为魔数0xCAFEBABE，并打印版本号
(defn check-class-file [in]
	(when (= (getMagicNumber in) "CAFEBABE")
			 (println "This is a JVM class file") 
			 true)
)

;;取版本号
(defn getVersion [in]
	(let [minor (.readUnsignedShort in)
		  major (.readUnsignedShort in)]
		  (str major "." minor)))

;;对class文件进行反编译
(defn decompiler [filename]
	(with-open [in (java.io.DataInputStream. (java.io.FileInputStream. filename))]
		(when (check-class-file in)
			 (println (str "Version is " (getVersion in)))
			 )


	)
)


;;执行
(decompiler "d:/work/jvm/testfiles/Helloworld.class")