<?php

namespace App\Form;

use App\Entity\Animal;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\DataTransformerInterface;
use Symfony\Component\Form\Exception\TransformationFailedException;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class AnimalType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('Animal_Name')
        ->add('Animal_Breed', ChoiceType::class, [
            'choices' => [
                'Labrador Retriever' => 'Labrador Retriever',
                'Persian ' => 'Persian ',
                'Guinea Pig' => 'Guinea Pig',
                'Cockatiel' => 'Cockatiel',
                'Betta Fish ' => 'Betta Fish ',
                'Bearded Dragon' => 'Bearded Dragon',
        
            ],
            'placeholder' => 'Select Breed',
            'required' => true,
        ])
        ->add('Animal_Status', ChoiceType::class, [
            'choices' => [
                'Available' => 'Available',
                'Pending' => 'Pending',
                'Adopted' => 'Adopted',
        
            ],
            'placeholder' => 'Select Status',
            'required' => true,
        ])
        ->add('Animal_Type', ChoiceType::class, [
            'choices' => [
                'Dog' => 'Dog',
                'Cat' => 'Cat',
                'Rabbit' => 'Rabbit',
                'Turtle' => 'Turtle',
                
            ],
            'placeholder' => 'Select Type',
            'required' => true,
        ])
            ->add('Age')
            ->add('Enrollement_Date')
            ->add('Animal_Description')
            ->add('Animal_Image', FileType::class, [
                'label' => 'Animal Image',
                'required' => false,
            ]);
            

        $builder->get('Animal_Image')->addModelTransformer(new class() implements DataTransformerInterface {
            public function transform($value)
            {
               
                if ($value instanceof \Symfony\Component\HttpFoundation\File\File) {
                    return $value->getFilename();
                }

                return null; 
            }

            public function reverseTransform($value)
            {
                
                if ($value instanceof \Symfony\Component\HttpFoundation\File\UploadedFile) {
                    return $value;
                }

                if (is_string($value)) {
                   
                    $uploadDirectory = $this->getParameter('kernel.project_dir') . '/public/animal_images/';
                    $filePath = $uploadDirectory . $value;

                    if (file_exists($filePath)) {
                        return new \Symfony\Component\HttpFoundation\File\File($filePath);
                    }

                    throw new TransformationFailedException("File not found: $value");
                }

                return null;
            }
        });
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Animal::class,
        ]);
    }
}
