Problem 1:

(define pairNode (lambda (fst snd) (lambda (op) (if op fst snd))))
(define node (lambda (x) (pairNode x (ref (list)))))
(define getFst (lambda (p) (p #t)))
(define getSnd (lambda (p) (p #f)))
(define head (node 1))

    Part (a) -
        (define num1 (ref 2736))
        (define num2 (ref 8))
        (define classNum (ref 1))
        (set! classNum (/ (deref num1) (deref num2)))
        (deref classNum)

    Part (b) -
        (i)
            (define find (lambda (head x) (if (= (getFst head) x) (getFst head) (if (isLast head) (list) (find (next head) x)))))
            (define isLast (lambda (n) (= (deref (getSnd n)) (list))))
            (define next (lambda (n) (deref (getSnd n))))

        (ii)
            (define insert (lambda (ele x) (if (null? x) (getFst head) (helper head ele x))))
            (define helper (lambda (head ele x) (if (null? head) (list) (if (= (getFst head) (getFst x)) (cons (getFst x) (cons (getFst ele) (helper (next head) ele x))) (cons (getFst head) (helper (next head) ele x))))))
            (define next (lambda (n) (deref (getSnd n))))