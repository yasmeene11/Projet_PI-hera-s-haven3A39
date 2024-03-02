<?php

namespace App\Repository;

use App\Entity\Animal;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Animal>
 *
 * @method Animal|null find($id, $lockMode = null, $lockVersion = null)
 * @method Animal|null findOneBy(array $criteria, array $orderBy = null)
 * @method Animal[]    findAll()
 * @method Animal[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class AnimalRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Animal::class);
    }

    /**
     * Get animals with a specific status.
     *
     * @param string $status
     * @return Animal[]
     */
    public function findByStatus(string $status): array
    {
        return $this->createQueryBuilder('a')
            ->andWhere('a.Animal_Status = :status')
            ->setParameter('status', $status)
            ->orderBy('a.Animal_Name', 'ASC')
            ->getQuery()
            ->getResult();
    }


    public function findbytype($type = null)
    {
        $queryBuilder = $this->createQueryBuilder('a')
            ->orderBy('a.Animal_Type', 'ASC');
    
        if ($type !== null) {
            $queryBuilder->andWhere('a.Animal_Type = :type')
                ->setParameter('type', $type);
        }
    
        return $queryBuilder->getQuery()->getResult();
    }
    public function findByName($name)
    {
        return $this->createQueryBuilder('a')
            ->andWhere('a.Animal_Name = :name')
            ->setParameter('name', $name)
            ->getQuery()
            ->getOneOrNullResult();
    }

    /**
     * @param string $userInput
     * @return Animal|null
     */
    public function findBestMatchAnimal(string $userInput): ?Animal
    {
        return $this->createQueryBuilder('a')
            ->andWhere('a.Animal_Name LIKE :userInput')
            ->setParameter('userInput', '%' . $userInput . '%')
            ->orderBy('a.matchingAlgorithmResult', 'DESC')  
            ->setMaxResults(1)
            ->getQuery()
            ->getOneOrNullResult();
    }
}